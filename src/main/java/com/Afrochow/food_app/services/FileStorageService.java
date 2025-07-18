package com.Afrochow.food_app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private static final List<String> ALLOWED_EXTENSIONS = List.of(".jpg", ".jpeg", ".png", ".pdf", ".avif");

    @Value("${app.file.upload-dir}")
    private String uploadDir;

    private String saveFileToSubdir(MultipartFile file, String subDir, String baseUrl) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Cannot save empty file");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

            if (!ALLOWED_EXTENSIONS.contains(extension)) {
                throw new IOException("File type not allowed: " + extension);
            }
        } else {
            throw new IOException("File must have an extension");
        }

        String fileName = UUID.randomUUID() + extension;

        Path uploadPath = Paths.get(uploadDir, subDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            logger.info("Created upload directory at {}", uploadPath.toAbsolutePath());
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        logger.info("Saved file to: {}", filePath.toAbsolutePath());

        return baseUrl + fileName;
    }

    public String saveProductImage(MultipartFile file) throws IOException {
        return saveFileToSubdir(file, "products", "http://localhost:8080/uploads/products/");
    }

    public String saveVendorLogo(MultipartFile file) throws IOException {
        return saveFileToSubdir(file, "Store-logo", "http://localhost:8080/uploads/Store-logo/");
    }

    public String saveBusinessLogo(MultipartFile file) throws IOException {
        return saveFileToSubdir(file, "businessLogo", "http://localhost:8080/uploads/businessLogo/");
    }

    public String saveIdCardFront(MultipartFile file) throws IOException {
        return saveFileToSubdir(file, "idFront", "http://localhost:8080/uploads/idFront/");
    }

    public String saveIdCardBack(MultipartFile file) throws IOException {
        return saveFileToSubdir(file, "idBack", "http://localhost:8080/uploads/idBack/");
    }

}