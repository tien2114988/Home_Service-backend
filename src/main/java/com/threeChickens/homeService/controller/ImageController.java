//package com.threeChickens.homeService.controller;
//
//import com.threeChickens.homeService.utils.FileUploadUtil;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.apache.tomcat.util.http.fileupload.FileUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.MalformedURLException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//@Tag(name = "Image", description = "Access images through url")
//@RequestMapping("api/images")
//public class ImageController {
//    @Autowired
//    private FileUploadUtil fileUploadUtil;
//
//    @GetMapping("/images/{fileName}")
//    public ResponseEntity<String> getImage(@PathVariable String fileName) {
//        String image = fileUploadUtil.convertFileToBase64(fileName);
//        return ResponseEntity.ok(image);
//    }
//}
