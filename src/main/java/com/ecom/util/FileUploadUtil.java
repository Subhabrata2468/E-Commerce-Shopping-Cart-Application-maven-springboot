package com.ecom.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadUtil {

    @Value("${upload.dir:/app/uploads}")
    private String uploadDir;

    /**
     * Save file to the specified subdirectory within uploads
     * @param file The file to save
     * @param subDir The subdirectory (e.g., "profile_img", "product_img", "category_img")
     * @return The filename if saved successfully, null otherwise
     */
    public String saveFile(MultipartFile file, String subDir) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            // Create the full directory path
            Path uploadPath = Paths.get(uploadDir, subDir);
            Files.createDirectories(uploadPath);

            // Generate unique filename to avoid conflicts
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            
            String filename = System.currentTimeMillis() + fileExtension;
            Path filePath = uploadPath.resolve(filename);

            // Save the file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Delete a file from the specified subdirectory
     * @param filename The filename to delete
     * @param subDir The subdirectory
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteFile(String filename, String subDir) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }

        try {
            Path filePath = Paths.get(uploadDir, subDir, filename);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get the full path to a file
     * @param filename The filename
     * @param subDir The subdirectory
     * @return The full path as a string
     */
    public String getFilePath(String filename, String subDir) {
        return Paths.get(uploadDir, subDir, filename).toString();
    }

    /**
     * Check if a file exists
     * @param filename The filename
     * @param subDir The subdirectory
     * @return true if file exists, false otherwise
     */
    public boolean fileExists(String filename, String subDir) {
        if (filename == null || filename.isEmpty()) {
            return false;
        }
        Path filePath = Paths.get(uploadDir, subDir, filename);
        return Files.exists(filePath);
    }
}
