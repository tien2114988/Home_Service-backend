//package com.larkEWA.utils;
//
//import jakarta.annotation.PostConstruct;
//import org.apache.commons.io.FileUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.file.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Base64;
//import java.util.Objects;
//
//@Component
//public class FileUploadUtil {
//
//    @Value("${upload.dir}")
//    private String uploadDir;
//
//    private Path root;
//
//    @PostConstruct
//    public void init() {
//        // Initialize the root path from an external configuration
//        this.root = Paths.get(Objects.requireNonNull(uploadDir, "upload.dir must not be null"));
//        try {
//            if (!Files.exists(root)) {
//                Files.createDirectories(root);
//            }
//            Path companyLogoPath = this.root.resolve("companyLogo");
//            if (!Files.exists(companyLogoPath)) {
//                Files.createDirectories(companyLogoPath);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Could not initialize folder for upload!", e);
//        }
//    }
//
//    public String saveLogo(MultipartFile file, String fileName) {
//        try {
//            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//            String originalFileName = file.getOriginalFilename();
//            if (originalFileName == null) {
//                throw new IllegalArgumentException("Original file name is null");
//            }
//            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
//            String newFileName = fileName + "_" + timestamp + fileExtension;
//
//            Path filePath = this.root.resolve("companyLogo").resolve(Objects.requireNonNull(newFileName));
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//            return filePath.toString();
//        } catch (IOException e) {
//            throw new RuntimeException("Could not save file: " + e.getMessage(), e);
//        }
//    }
//
//    public String convertFileToBase64(String filePath) {
//        try {
//            File file = new File(filePath);
//            if (!file.exists()) {
//                return "";
//            }
//            try (FileInputStream fileInputStream = new FileInputStream(file)) {
//                byte[] bytes = new byte[(int) file.length()];
//                fileInputStream.read(bytes);
//                return Base64.getEncoder().encodeToString(bytes);
//            }
//        } catch (IOException e) {
//            return "";
//        }
//    }
//}
