package com.example.blob.repositories;
import java.util.ArrayList;
import com.example.blob.dao.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findByPhotoIsNull();
}