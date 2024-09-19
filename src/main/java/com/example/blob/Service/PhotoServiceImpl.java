package com.example.blob.Service;
import java.util.ArrayList;
import com.example.blob.dao.Employee;
import com.example.blob.dao.LogEntry;
import com.example.blob.repositories.EmployeeRepository;
import com.example.blob.repositories.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LogEntryRepository logEntryRepository;

    @Override
    public void uploadPhotos(Path photosDirectory) {
        try {
            Files.list(photosDirectory).forEach(photoPath -> {
                String matricule = extractMatricule(photoPath.getFileName().toString());
                Employee employee = employeeRepository.findById(matricule)
                        .orElseThrow(() -> new RuntimeException("Employee not found"));

                byte[] photoBytes;
                try {
                    photoBytes = Files.readAllBytes(photoPath);
                } catch (IOException e) {
                    throw new RuntimeException("Error reading photo file", e);
                }
                employee.setPhoto(photoBytes);  // This should work since Lombok generates this method
                employeeRepository.save(employee);

                logEntryRepository.save(new LogEntry("Uploaded photo for " + matricule, LocalDateTime.now()));
            });
        } catch (IOException e) {
            throw new RuntimeException("Error uploading photos", e);
        }
    }

    @Override
    public List<LogEntry> getLatestLogs() {
        return logEntryRepository.findTop10ByOrderByTimestampDesc();
    }

    private String extractMatricule(String fileName) {
        return fileName.substring(0, fileName.indexOf('.'));
    }
}