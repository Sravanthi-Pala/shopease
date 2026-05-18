package com.ecommerce.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("???  SHOPEASE IS RUNNING!");
        System.out.println("?? http://localhost:8080");
        System.out.println("?? Admin: admin@shopease.com / admin123");
        System.out.println("?? User: user@shopease.com / user123");
        System.out.println("========================================\n");
    }
}
