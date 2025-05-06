package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // your React frontend port
public class UploadImage {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Save the uploaded file to the uploads/ folder
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.createDirectories(path.getParent()); // create uploads/ if not exist
            Files.write(path, file.getBytes());
            return "http://localhost:8080/" + UPLOAD_DIR + file.getOriginalFilename(); // return public URL
        } catch (Exception e) {
            e.printStackTrace();
            return "Upload failed!";
        }
    }
}
