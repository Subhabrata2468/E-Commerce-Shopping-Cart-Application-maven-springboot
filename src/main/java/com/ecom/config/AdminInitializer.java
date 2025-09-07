package com.ecom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecom.model.UserDtls;
import com.ecom.service.UserService;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.mobile}")
    private String adminMobile;

    @Value("${admin.address}")
    private String adminAddress;

    @Value("${admin.city}")
    private String adminCity;

    @Value("${admin.state}")
    private String adminState;

    @Value("${admin.pincode}")
    private String adminPincode;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists
        UserDtls existingAdmin = userService.getUserByEmail(adminEmail);
        
        if (existingAdmin == null) {
            // Create admin user from properties
            UserDtls admin = new UserDtls();
            admin.setName(adminName);
            admin.setEmail(adminEmail);
            admin.setPassword(adminPassword);
            admin.setMobileNumber(adminMobile);
            admin.setAddress(adminAddress);
            admin.setCity(adminCity);
            admin.setState(adminState);
            admin.setPincode(adminPincode);
            
            userService.saveAdmin(admin);
            System.out.println("Admin user created successfully from configuration!");
            System.out.println("Email: " + adminEmail);
            System.out.println("Password: " + adminPassword);
        } else {
            System.out.println("Admin user already exists!");
        }
        
        // Create some sample users for testing
        createSampleUsers();
    }
    
    private void createSampleUsers() {
        // Check if sample users already exist
        if (userService.getUsers("ROLE_USER").isEmpty()) {
            System.out.println("Creating sample users...");
            
            // Sample User 1
            UserDtls user1 = new UserDtls();
            user1.setName("John Doe");
            user1.setEmail("john.doe@example.com");
            user1.setPassword("password123");
            user1.setMobileNumber("1234567890");
            user1.setAddress("123 Main St");
            user1.setCity("New York");
            user1.setState("NY");
            user1.setPincode("10001");
            userService.saveUser(user1);
            
            // Sample User 2
            UserDtls user2 = new UserDtls();
            user2.setName("Jane Smith");
            user2.setEmail("jane.smith@example.com");
            user2.setPassword("password123");
            user2.setMobileNumber("0987654321");
            user2.setAddress("456 Oak Ave");
            user2.setCity("Los Angeles");
            user2.setState("CA");
            user2.setPincode("90210");
            userService.saveUser(user2);
            
            // Sample User 3
            UserDtls user3 = new UserDtls();
            user3.setName("Bob Johnson");
            user3.setEmail("bob.johnson@example.com");
            user3.setPassword("password123");
            user3.setMobileNumber("5555555555");
            user3.setAddress("789 Pine St");
            user3.setCity("Chicago");
            user3.setState("IL");
            user3.setPincode("60601");
            userService.saveUser(user3);
            
            System.out.println("Sample users created successfully!");
        } else {
            System.out.println("Sample users already exist!");
        }
    }
}
