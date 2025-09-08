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
        
    }
    
    
}
