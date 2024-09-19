package com.example.blob.Service;
import java.util.ArrayList;
import com.example.blob.dao.LogEntry;
import java.nio.file.Path;
import java.util.List;

public interface PhotoService {
    void uploadPhotos(Path photosDirectory);
    List<LogEntry> getLatestLogs();
}