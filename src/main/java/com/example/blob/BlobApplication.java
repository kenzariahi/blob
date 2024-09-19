package com.example.blob;
import java.util.ArrayList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.blob.dao") // Specify the package of your entities

public class BlobApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlobApplication.class, args);
    }
}