package com.example.blob.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String loginPage() {
        return "/login"; // This should return the login page view
    }

    @PostMapping("/login")
    public String loginSuccess() {
        // Redirect to /upload page after successful login
        return "redirect:/admin/upload";
    }
}