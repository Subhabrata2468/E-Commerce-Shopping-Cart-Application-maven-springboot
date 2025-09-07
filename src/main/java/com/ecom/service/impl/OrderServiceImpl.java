package com.ecom.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecom.model.Cart;
import com.ecom.model.OrderAddress;
import com.ecom.model.OrderRequest;
import com.ecom.model.ProductOrder;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductOrderRepository;
import com.ecom.service.OrderService;
import com.ecom.util.CommonUtil;
import com.ecom.util.OrderStatus;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductOrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CommonUtil commonUtil;

	@Override
	public void saveOrder(Integer userid, OrderRequest orderRequest) throws Exception {

		List<Cart> carts = cartRepository.findByUserId(userid);

		for (Cart cart : carts) {

			ProductOrder order = new ProductOrder();

			order.setOrderId(UUID.randomUUID().toString());
			order.setOrderDate(LocalDate.now());

			order.setProduct(cart.getProduct());
			order.setPrice(cart.getProduct().getDiscountPrice());

			order.setQuantity(cart.getQuantity());
			order.setUser(cart.getUser());

			order.setStatus(OrderStatus.IN_PROGRESS.getName());
			order.setPaymentType(orderRequest.getPaymentType());

			OrderAddress address = new OrderAddress();
			address.setFirstName(orderRequest.getFirstName());
			address.setLastName(orderRequest.getLastName());
			address.setEmail(orderRequest.getEmail());
			address.setMobileNo(orderRequest.getMobileNo());
			address.setAddress(orderRequest.getAddress());
			address.setCity(orderRequest.getCity());
			address.setState(orderRequest.getState());
			address.setPincode(orderRequest.getPincode());

			order.setOrderAddress(address);

			ProductOrder saveOrder = orderRepository.save(order);
			commonUtil.sendMailForProductOrder(saveOrder, "success");
		}
	}

	@Override
	public List<ProductOrder> getOrdersByUser(Integer userId) {
		List<ProductOrder> orders = orderRepository.findByUserId(userId);
		return orders;
	}

	@Override
	public ProductOrder updateOrderStatus(Integer id, String status) {
		try {
			System.out.println("OrderServiceImpl: Updating order ID " + id + " to status: " + status);
			
			Optional<ProductOrder> findById = orderRepository.findById(id);
			if (findById.isPresent()) {
				ProductOrder productOrder = findById.get();
				System.out.println("OrderServiceImpl: Found order " + productOrder.getOrderId() + " with current status: " + productOrder.getStatus());
				
				productOrder.setStatus(status);
				ProductOrder updateOrder = orderRepository.save(productOrder);
				
				System.out.println("OrderServiceImpl: Order updated successfully with new status: " + updateOrder.getStatus());
				return updateOrder;
			} else {
				System.err.println("OrderServiceImpl: Order with ID " + id + " not found");
			}
		} catch (Exception e) {
			System.err.println("OrderServiceImpl: Error updating order status: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProductOrder> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Page<ProductOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize) {
		try {
			System.out.println("OrderServiceImpl: Getting orders - page: " + pageNo + ", size: " + pageSize);
			
			// Sort by newest orders first (by orderDate desc, then by id desc)
			Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "orderDate", "id"));
			Page<ProductOrder> result = orderRepository.findAll(pageable);
			
			System.out.println("OrderServiceImpl: Found " + result.getTotalElements() + " total orders, " + result.getContent().size() + " on this page");
			
			// Debug: Print first few orders
			if (!result.getContent().isEmpty()) {
				for (int i = 0; i < Math.min(3, result.getContent().size()); i++) {
					ProductOrder order = result.getContent().get(i);
					System.out.println("OrderServiceImpl: Order " + (i+1) + " - ID: " + order.getId() + ", OrderID: " + order.getOrderId() + ", Status: " + order.getStatus() + ", Date: " + order.getOrderDate());
				}
			}
			
			return result;
		} catch (Exception e) {
			System.err.println("OrderServiceImpl: Error getting orders: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ProductOrder getOrdersByOrderId(String orderId) {
		return orderRepository.findByOrderId(orderId);
	}

	@Override
	public ProductOrder saveOrder(ProductOrder order) {
		return orderRepository.save(order);
	}

}
