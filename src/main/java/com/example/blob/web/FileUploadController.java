package com.example.blob.web;

import com.example.blob.Service.HRAccessClient;
import com.example.blob.Service.PhotoService;
import com.example.blob.Service.HrConfig;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;

@Controller
@RequestMapping("/admin")
public class FileUploadController {

    @Autowired
    private HRAccessClient hrAccessClient;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private HrConfig hrConfig;  // Autowire HrConfig for dynamic property access

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("message", "");
        return "upload";
    }

    @PostMapping("/uploadHRAccessFiles")
    public String handleFileUpload(
            @RequestParam("photoDir") MultipartFile photoDir,
            @RequestParam("log4jFile") MultipartFile log4jFile,
            @RequestParam("openhrFile") MultipartFile openhrFile,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {

        // Retrieve the username of the currently logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = ((User) authentication.getPrincipal()).getUsername();

        try {
            // Use loggedInUsername where needed
            // For example, validating or logging the username
            System.out.println("Logged in user: " + loggedInUsername);

            // Existing file handling logic...
            if (photoDir != null && !photoDir.isEmpty()) {
                File photosDirectory = new File(photoDir.getOriginalFilename());
                if (!photosDirectory.exists()) {
                    redirectAttributes.addFlashAttribute("message", "Photo directory does not exist.");
                    return "redirect:/admin/upload";
                }
                photoDir.transferTo(photosDirectory);
                Path photosPath = Paths.get(photosDirectory.getAbsolutePath());
                photoService.uploadPhotos(photosPath);
            }

            if (log4jFile != null && !log4jFile.isEmpty()) {
                File log4j = new File(log4jFile.getOriginalFilename());
                log4jFile.transferTo(log4j);
                reloadLog4jConfiguration(log4j.getAbsolutePath());
            }

            if (openhrFile != null && !openhrFile.isEmpty()) {
                File openhr = new File(openhrFile.getOriginalFilename());
                openhrFile.transferTo(openhr);

                Properties properties = loadProperties(openhr.getAbsolutePath());
                String hrAccessUrl = properties.getProperty("hraccess.url");
                String hrAccessUsername = properties.getProperty("hraccess.username");
                String hrAccessPassword = properties.getProperty("hraccess.password");

                hrAccessClient.connect(hrAccessUrl, hrAccessUsername, hrAccessPassword);
            }

            redirectAttributes.addFlashAttribute("message", "Files uploaded successfully!");
            return "redirect:/admin/logs";

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to upload files.");
            return "redirect:/admin/upload";
        }
    }

    private Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        }
        return properties;
    }

    private void reloadLog4jConfiguration(String filePath) {
        PropertyConfigurator.configure(filePath);
    }

    @GetMapping("/admin/upload")
    public String showUploadPage(Model model, Principal principal) {
        String username = principal.getName(); // or however you get the username
        model.addAttribute("username", username);
        return "upload";
    }

}