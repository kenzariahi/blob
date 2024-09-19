package com.example.blob.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/home")
    public String adminHome(Model model) {
        // Retrieve the username of the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = ((User) authentication.getPrincipal()).getUsername();

        // Add the username to the model
        model.addAttribute("loggedInUsername", loggedInUsername);

        return "upload";
    }



    @PostMapping("/uploadFiles")
    public String handleFileUpload(
            @RequestParam("photoDir") MultipartFile[] photoFiles,
            @RequestParam("log4jFile") MultipartFile log4jFile,
            @RequestParam("openhrFile") MultipartFile openhrFile,
            Model model) {

        StringBuilder photoPaths = new StringBuilder();
        String log4jPath = "";
        String openhrPath = "";

        try {
            // Process the Photo Directory
            if (photoFiles != null && photoFiles.length > 0) {
                for (MultipartFile file : photoFiles) {
                    if (!file.isEmpty()) {
                        File photoFile = new File(file.getOriginalFilename());
                        file.transferTo(photoFile);
                        photoPaths.append(photoFile.getAbsolutePath()).append(", ");
                    }
                }
            }

            // Process the log4j.properties file
            if (log4jFile != null && !log4jFile.isEmpty()) {
                File log4j = new File(log4jFile.getOriginalFilename());
                log4jFile.transferTo(log4j);
                log4jPath = log4j.getAbsolutePath();
            }

            // Process the openhr.properties file
            if (openhrFile != null && !openhrFile.isEmpty()) {
                File openhr = new File(openhrFile.getOriginalFilename());
                openhrFile.transferTo(openhr);
                openhrPath = openhr.getAbsolutePath();
            }

            model.addAttribute("message", "Files uploaded successfully!");
            model.addAttribute("photoPaths", photoPaths.toString());
            model.addAttribute("log4jPath", log4jPath);
            model.addAttribute("openhrPath", openhrPath);

        } catch (IOException e) {
            model.addAttribute("message", "File upload failed: " + e.getMessage());
            e.printStackTrace();
        }

        return "adminHomePage";
    }
}