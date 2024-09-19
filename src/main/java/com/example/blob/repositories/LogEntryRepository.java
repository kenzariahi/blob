package com.example.blob.repositories;
import java.util.ArrayList;
import com.example.blob.dao.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findByOrderByTimestampDesc();

    List<LogEntry> findTop10ByOrderByTimestampDesc();

}