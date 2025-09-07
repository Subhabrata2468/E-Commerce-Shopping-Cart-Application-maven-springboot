package com.ecom.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Utility class to help determine the correct image path
 * for both static and uploaded images
 */
@Component
public class ImagePathUtil {

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * Get the correct image path for a given image name and type
     * @param imageName The image filename
     * @param imageType The type of image (profile_img, product_img, category_img)
     * @return The complete URL path to the image
     */
    public String getImagePath(String imageName, String imageType) {
        if (imageName == null || imageName.isEmpty() || "default.jpg".equals(imageName)) {
            return "/img/" + imageType + "/" + imageName;
        }

        // Prefer uploads for any non-default image name; this avoids incorrect fallbacks
        // to classpath static when the file is actually stored under /uploads/** in Docker.
        return "/uploads/" + imageType + "/" + imageName;
    }

    /**
     * Get the correct image path for profile images
     * @param imageName The profile image filename
     * @return The complete URL path to the profile image
     */
    public String getProfileImagePath(String imageName) {
        return getImagePath(imageName, "profile_img");
    }

    /**
     * Get the correct image path for product images
     * @param imageName The product image filename
     * @return The complete URL path to the product image
     */
    public String getProductImagePath(String imageName) {
        return getImagePath(imageName, "product_img");
    }

    /**
     * Get the correct image path for category images
     * @param imageName The category image filename
     * @return The complete URL path to the category image
     */
    public String getCategoryImagePath(String imageName) {
        return getImagePath(imageName, "category_img");
    }
}
