package com.example.blob.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LogController {

    @GetMapping("/admin/logs")
    public String showLogs(Model model) {
        // This method will fetch logs and display them on the logs page.
        // You can fetch logs from your service or database here.
        // Placeholder logic for now
        List<String> logs = new ArrayList<>();
        logs.add("Entrée de log 1: X opération performée");
        logs.add("Entrée de log 2: Y opération performée");

        // Add the logs to the model so they can be displayed in the logs.html view
        model.addAttribute("logs", logs);

        return "logs"; // Return the logs.html template
    }
}