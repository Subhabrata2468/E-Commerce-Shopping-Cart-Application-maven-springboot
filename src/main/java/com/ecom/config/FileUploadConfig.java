package com.ecom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class FileUploadConfig implements WebMvcConfigurer {

    @Value("${upload.dir:/app/uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // Serve uploaded files as static resources
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/");
        // Backward-compat: serve direct /profile_img/**, /product_img/**, /category_img/**
        registry.addResourceHandler("/profile_img/**")
                .addResourceLocations("file:" + uploadDir + "/profile_img/");
        registry.addResourceHandler("/product_img/**")
                .addResourceLocations("file:" + uploadDir + "/product_img/");
        registry.addResourceHandler("/category_img/**")
                .addResourceLocations("file:" + uploadDir + "/category_img/");
        
        // Explicit handlers for classpath static assets
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }
}
