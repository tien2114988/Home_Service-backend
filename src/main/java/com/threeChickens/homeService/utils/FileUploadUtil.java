package com.threeChickens.homeService.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;

@Component
public class FileUploadUtil {

    @Value("${upload.dir}")
    private String uploadDir; // uploadDir sẽ trỏ đến thư mục images

    private Path root;

    @PostConstruct
    public void init() {
        // Initialize the root path from an external configuration
        this.root = Paths.get(Objects.requireNonNull(uploadDir, "upload.dir must not be null"));
        try {
            // Tạo thư mục images nếu chưa tồn tại
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!", e);
        }
    }

    public Resource getImage(String filename) throws MalformedURLException {
        Path file = root.resolve(filename);
        return new UrlResource(file.toUri());
    }

    public String saveImage(MultipartFile file, String fileName) {
        try {
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null) {
                throw new IllegalArgumentException("Original file name is null");
            }
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = fileName + fileExtension;

            // Lưu file vào thư mục images
            Path filePath = this.root.resolve(Objects.requireNonNull(newFileName));
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Could not save file: " + e.getMessage(), e);
        }
    }

    public String convertFileToBase64(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return "";
            }
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] bytes = new byte[(int) file.length()];
                fileInputStream.read(bytes);
                return Base64.getEncoder().encodeToString(bytes);
            }
        } catch (IOException e) {
            return "";
        }
    }
}
