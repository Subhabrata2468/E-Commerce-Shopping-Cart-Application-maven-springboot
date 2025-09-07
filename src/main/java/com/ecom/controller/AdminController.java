package com.ecom.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.model.OrderAddress;
import com.ecom.model.Product;
import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.service.CartService;
import com.ecom.service.CategoryService;
import com.ecom.service.OrderService;
import com.ecom.service.ProductService;
import com.ecom.service.UserService;
import com.ecom.util.CommonUtil;
import com.ecom.util.FileUploadUtil;
import com.ecom.util.OrderStatus;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private FileUploadUtil fileUploadUtil;

	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			UserDtls userDtls = userService.getUserByEmail(email);
			m.addAttribute("user", userDtls);
			Integer countCart = cartService.getCountCart(userDtls.getId());
			m.addAttribute("countCart", countCart);
		}

		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}

	@GetMapping("/")
	public String index() {
		return "admin/index";
	}

	@GetMapping("/loadAddProduct")
	public String loadAddProduct(Model m) {
		List<Category> categories = categoryService.getAllCategory();
		m.addAttribute("categories", categories);
		return "admin/add_product";
	}

	@GetMapping("/category")
	public String category(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		// m.addAttribute("categorys", categoryService.getAllCategory());
		Page<Category> page = categoryService.getAllCategorPagination(pageNo, pageSize);
		List<Category> categorys = page.getContent();
		m.addAttribute("categorys", categorys);

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "admin/category";
	}

	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		Boolean existsName = categoryService.existCategory(category.getName());

		if (existsName) {
			session.setAttribute("errorMsg", "Category Name already exists");
		} else {

			Category saveCategory = categoryService.saveCategory(category);

			if (ObjectUtils.isEmpty(saveCategory)) {
				session.setAttribute("errorMsg", "Not saved ! internal server error");
			} else {

				if (!file.isEmpty()) {
					// Use the new file upload utility
					String savedFilename = fileUploadUtil.saveFile(file, "category_img");
					if (savedFilename != null) {
						// Update the category with the new filename
						saveCategory.setImageName(savedFilename);
						categoryService.saveCategory(saveCategory);
					}
				}

				session.setAttribute("succMsg", "Saved successfully");
			}
		}

		return "redirect:/admin/category";
	}

	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id, HttpSession session) {
		Boolean deleteCategory = categoryService.deleteCategory(id);

		if (deleteCategory) {
			session.setAttribute("succMsg", "category delete success");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/category";
	}

	@GetMapping("/loadEditCategory/{id}")
	public String loadEditCategory(@PathVariable int id, Model m) {
		m.addAttribute("category", categoryService.getCategoryById(id));
		return "admin/edit_category";
	}

	@PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		Category oldCategory = categoryService.getCategoryById(category.getId());
		String imageName = oldCategory.getImageName();

		if (!ObjectUtils.isEmpty(category)) {

			oldCategory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());
			
			if (!file.isEmpty()) {
				// Use the new file upload utility
				String savedFilename = fileUploadUtil.saveFile(file, "category_img");
				if (savedFilename != null) {
					imageName = savedFilename;
				}
			}
			
			oldCategory.setImageName(imageName);
		}

		Category updateCategory = categoryService.saveCategory(oldCategory);

		if (!ObjectUtils.isEmpty(updateCategory)) {
			session.setAttribute("succMsg", "Category update success");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/loadEditCategory/" + category.getId();
	}

	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
			HttpSession session) throws IOException {

		String imageName = "default.jpg";
		if (!image.isEmpty()) {
			// Use the new file upload utility
			String savedFilename = fileUploadUtil.saveFile(image, "product_img");
			if (savedFilename != null) {
				imageName = savedFilename;
			}
		}

		product.setImage(imageName);
		product.setDiscount(0);
		product.setDiscountPrice(product.getPrice());
		Product saveProduct = productService.saveProduct(product);

		if (!ObjectUtils.isEmpty(saveProduct)) {
			session.setAttribute("succMsg", "Product Saved Success");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/loadAddProduct";
	}

	@GetMapping("/products")
	public String loadViewProduct(Model m, @RequestParam(defaultValue = "") String ch,
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

//		List<Product> products = null;
//		if (ch != null && ch.length() > 0) {
//			products = productService.searchProduct(ch);
//		} else {
//			products = productService.getAllProducts();
//		}
//		m.addAttribute("products", products);

		Page<Product> page = null;
		if (ch != null && ch.length() > 0) {
			page = productService.searchProductPagination(pageNo, pageSize, ch);
		} else {
			page = productService.getAllProductsPagination(pageNo, pageSize);
		}
		m.addAttribute("products", page.getContent());

		m.addAttribute("pageNo", page.getNumber());
		m.addAttribute("pageSize", pageSize);
		m.addAttribute("totalElements", page.getTotalElements());
		m.addAttribute("totalPages", page.getTotalPages());
		m.addAttribute("isFirst", page.isFirst());
		m.addAttribute("isLast", page.isLast());

		return "admin/products";
	}

	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id, HttpSession session) {
		Boolean deleteProduct = productService.deleteProduct(id);
		if (deleteProduct) {
			session.setAttribute("succMsg", "Product delete success");
		} else {
			session.setAttribute("errorMsg", "Something wrong on server");
		}
		return "redirect:/admin/products";
	}

	@GetMapping("/editProduct/{id}")
	public String editProduct(@PathVariable int id, Model m) {
		m.addAttribute("product", productService.getProductById(id));
		m.addAttribute("categories", categoryService.getAllCategory());
		return "admin/edit_product";
	}

	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
			HttpSession session, Model m) {

		if (product.getDiscount() < 0 || product.getDiscount() > 100) {
			session.setAttribute("errorMsg", "invalid Discount");
		} else {
			Product updateProduct = productService.updateProduct(product, image);
			if (!ObjectUtils.isEmpty(updateProduct)) {
				session.setAttribute("succMsg", "Product update success");
			} else {
				session.setAttribute("errorMsg", "Something wrong on server");
			}
		}
		return "redirect:/admin/editProduct/" + product.getId();
	}

	@GetMapping("/users")
	public String getAllUsers(Model m, @RequestParam(required = false, defaultValue = "1") Integer type) {
		try {
			List<UserDtls> users = null;
			if (type == 1) {
				users = userService.getUsers("ROLE_USER");
				System.out.println("Found " + users.size() + " users with ROLE_USER");
			} else {
				users = userService.getUsers("ROLE_ADMIN");
				System.out.println("Found " + users.size() + " users with ROLE_ADMIN");
			}
			
			// Debug: Print user details
			if (users != null) {
				for (UserDtls user : users) {
					System.out.println("User: " + user.getName() + " - " + user.getEmail() + " - " + user.getRole());
				}
			}
			
			m.addAttribute("userType", type);
			m.addAttribute("users", users != null ? users : new ArrayList<>());
			return "admin/users";
		} catch (Exception e) {
			System.err.println("Error in getAllUsers: " + e.getMessage());
			e.printStackTrace();
			m.addAttribute("userType", type);
			m.addAttribute("users", new ArrayList<>());
			m.addAttribute("errorMsg", "Error loading users: " + e.getMessage());
			return "admin/users";
		}
	}

	@GetMapping("/updateSts")
	public String updateUserAccountStatus(@RequestParam Boolean status, @RequestParam Integer id,@RequestParam Integer type, HttpSession session) {
		Boolean f = userService.updateAccountStatus(id, status);
		if (f) {
			session.setAttribute("succMsg", "Account Status Updated");
		} else {
			session.setAttribute("errorMsg", "Something wrong on server");
		}
		return "redirect:/admin/users?type="+type;
	}

	@GetMapping("/create-test-users")
	public String createTestUsers(HttpSession session) {
		try {
			// Check if users already exist
			List<UserDtls> existingUsers = userService.getUsers("ROLE_USER");
			if (existingUsers.isEmpty()) {
				// Create sample users
				UserDtls user1 = new UserDtls();
				user1.setName("Test User 1");
				user1.setEmail("test1@example.com");
				user1.setPassword("password123");
				user1.setMobileNumber("1111111111");
				user1.setAddress("Test Address 1");
				user1.setCity("Test City");
				user1.setState("Test State");
				user1.setPincode("12345");
				userService.saveUser(user1);

				UserDtls user2 = new UserDtls();
				user2.setName("Test User 2");
				user2.setEmail("test2@example.com");
				user2.setPassword("password123");
				user2.setMobileNumber("2222222222");
				user2.setAddress("Test Address 2");
				user2.setCity("Test City 2");
				user2.setState("Test State 2");
				user2.setPincode("54321");
				userService.saveUser(user2);

				session.setAttribute("succMsg", "Test users created successfully!");
			} else {
				session.setAttribute("succMsg", "Users already exist in database!");
			}
		} catch (Exception e) {
			session.setAttribute("errorMsg", "Error creating test users: " + e.getMessage());
		}
		return "redirect:/admin/users?type=1";
	}

	@GetMapping("/create-test-orders")
	public String createTestOrders(HttpSession session) {
		try {
			// Check if orders already exist
			List<ProductOrder> existingOrders = orderService.getAllOrders();
			if (existingOrders.isEmpty()) {
				// Get some users and products for creating orders
				List<UserDtls> users = userService.getUsers("ROLE_USER");
				List<Product> products = productService.getAllProducts();
				
				if (!users.isEmpty() && !products.isEmpty()) {
					// Create sample orders
					for (int i = 0; i < 3; i++) {
						ProductOrder order = new ProductOrder();
						order.setOrderId("ORD-" + System.currentTimeMillis() + "-" + i);
						order.setOrderDate(java.time.LocalDate.now());
						order.setProduct(products.get(i % products.size()));
						order.setPrice(products.get(i % products.size()).getDiscountPrice());
						order.setQuantity(1 + i);
						order.setUser(users.get(i % users.size()));
						order.setStatus("In Progress");
						order.setPaymentType("Cash on Delivery");
						
						// Create order address
						OrderAddress address = new OrderAddress();
						address.setFirstName("Test");
						address.setLastName("User " + (i + 1));
						address.setEmail("test" + (i + 1) + "@example.com");
						address.setMobileNo("123456789" + i);
						address.setAddress("Test Address " + (i + 1));
						address.setCity("Test City " + (i + 1));
						address.setState("Test State " + (i + 1));
						address.setPincode("12345" + i);
						
						order.setOrderAddress(address);
						
						// Save order
						ProductOrder savedOrder = orderService.saveOrder(order);
						System.out.println("Created test order: " + savedOrder.getOrderId());
					}
					
					session.setAttribute("succMsg", "Test orders created successfully!");
				} else {
					session.setAttribute("errorMsg", "No users or products found. Please create some first.");
				}
			} else {
				session.setAttribute("succMsg", "Orders already exist in database!");
			}
		} catch (Exception e) {
			session.setAttribute("errorMsg", "Error creating test orders: " + e.getMessage());
		}
		return "redirect:/admin/orders";
	}

	@GetMapping("/orders")
	public String getAllOrders(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		
		try {
			System.out.println("Loading all orders - page: " + pageNo + ", size: " + pageSize);
			Page<ProductOrder> page = orderService.getAllOrdersPagination(pageNo, pageSize);
			System.out.println("Found " + page.getTotalElements() + " orders");
			
			// Debug: Print order details
			if (page.getContent() != null) {
				for (ProductOrder order : page.getContent()) {
					System.out.println("Order: " + order.getOrderId() + " - " + order.getStatus() + " - " + (order.getProduct() != null ? order.getProduct().getTitle() : "No Product"));
				}
			}
			
			m.addAttribute("orders", page.getContent() != null ? page.getContent() : new ArrayList<>());
			m.addAttribute("srch", false);

			m.addAttribute("pageNo", page.getNumber());
			m.addAttribute("pageSize", pageSize);
			m.addAttribute("totalElements", page.getTotalElements());
			m.addAttribute("totalPages", page.getTotalPages());
			m.addAttribute("isFirst", page.isFirst());
			m.addAttribute("isLast", page.isLast());

			return "admin/orders";
		} catch (Exception e) {
			System.err.println("Error in getAllOrders: " + e.getMessage());
			e.printStackTrace();
			m.addAttribute("orders", new ArrayList<>());
			m.addAttribute("srch", false);
			m.addAttribute("errorMsg", "Error loading orders: " + e.getMessage());
			return "admin/orders";
		}
	}

	@PostMapping("/update-order-status")
	public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {

		OrderStatus[] values = OrderStatus.values();
		String status = null;

		for (OrderStatus orderSt : values) {
			if (orderSt.getId().equals(st)) {
				status = orderSt.getName();
			}
		}

		ProductOrder updateOrder = orderService.updateOrderStatus(id, status);

		try {
			commonUtil.sendMailForProductOrder(updateOrder, status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!ObjectUtils.isEmpty(updateOrder)) {
			session.setAttribute("succMsg", "Status Updated");
		} else {
			session.setAttribute("errorMsg", "status not updated");
		}
		return "redirect:/admin/orders";
	}

	@GetMapping("/search-order")
	public String searchOrder(@RequestParam(required = false) String orderId, Model m, HttpSession session,
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

		if (orderId != null && orderId.length() > 0) {
			System.out.println("Searching for order: " + orderId);
			ProductOrder order = orderService.getOrdersByOrderId(orderId.trim());

			if (ObjectUtils.isEmpty(order)) {
				session.setAttribute("errorMsg", "Incorrect orderId");
				m.addAttribute("orderDtls", null);
			} else {
				m.addAttribute("orderDtls", order);
				System.out.println("Order found: " + order.getOrderId());
			}

			m.addAttribute("srch", true);
		} else {
			System.out.println("Loading all orders with pagination - page: " + pageNo + ", size: " + pageSize);
			Page<ProductOrder> page = orderService.getAllOrdersPagination(pageNo, pageSize);
			System.out.println("Found " + page.getTotalElements() + " orders");
			
			m.addAttribute("orders", page.getContent());
			m.addAttribute("srch", false);

			m.addAttribute("pageNo", page.getNumber());
			m.addAttribute("pageSize", pageSize);
			m.addAttribute("totalElements", page.getTotalElements());
			m.addAttribute("totalPages", page.getTotalPages());
			m.addAttribute("isFirst", page.isFirst());
			m.addAttribute("isLast", page.isLast());
		}
		return "admin/orders";
	}

	@GetMapping("/add-admin")
	public String loadAdminAdd(Model m) {
		try {
			m.addAttribute("user", new UserDtls());
			return "admin/add_admin";
		} catch (Exception e) {
			System.err.println("Error in loadAdminAdd: " + e.getMessage());
			e.printStackTrace();
			m.addAttribute("errorMsg", "Error loading add admin page: " + e.getMessage());
			return "admin/add_admin";
		}
	}

	@PostMapping("/save-admin")
	public String saveAdmin(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session)
			throws IOException {

		String imageName = "default.jpg";
		if (!file.isEmpty()) {
			// Use the new file upload utility
			String savedFilename = fileUploadUtil.saveFile(file, "profile_img");
			if (savedFilename != null) {
				imageName = savedFilename;
			}
		}
		
		user.setProfileImage(imageName);
		UserDtls saveUser = userService.saveAdmin(user);

		if (!ObjectUtils.isEmpty(saveUser)) {
			session.setAttribute("succMsg", "Register successfully");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/add-admin";
	}

	@GetMapping("/profile")
	public String profile() {
		return "admin/profile";
	}

	@PostMapping("/update-profile")
	public String updateProfile(@ModelAttribute UserDtls user, @RequestParam MultipartFile img, HttpSession session) {
		UserDtls updateUserProfile = userService.updateUserProfile(user, img);
		if (ObjectUtils.isEmpty(updateUserProfile)) {
			session.setAttribute("errorMsg", "Profile not updated");
		} else {
			session.setAttribute("succMsg", "Profile Updated");
		}
		return "redirect:/admin/profile";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
			HttpSession session) {
		UserDtls loggedInUserDetails = commonUtil.getLoggedInUserDetails(p);

		boolean matches = passwordEncoder.matches(currentPassword, loggedInUserDetails.getPassword());

		if (matches) {
			String encodePassword = passwordEncoder.encode(newPassword);
			loggedInUserDetails.setPassword(encodePassword);
			UserDtls updateUser = userService.updateUser(loggedInUserDetails);
			if (ObjectUtils.isEmpty(updateUser)) {
				session.setAttribute("errorMsg", "Password not updated !! Error in server");
			} else {
				session.setAttribute("succMsg", "Password Updated sucessfully");
			}
		} else {
			session.setAttribute("errorMsg", "Current Password incorrect");
		}

		return "redirect:/admin/profile";
	}

}
