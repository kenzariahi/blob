package com.example.blob.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UploadController {

    @GetMapping("/admin/uploadPage")
    public String showUploadPage(Model model) {
        // Get the username from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Retrieves the username of the authenticated user

        // Add the username to the model
        model.addAttribute("username", username);

        // Return the name of the Thymeleaf template to render
        return "upload";
    }
}