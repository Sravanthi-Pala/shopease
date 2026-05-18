package com.ecommerce.demo.controller;

import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TestApiController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/test-products")
    public List<Product> testProducts() {
        return productRepository.findAll();
    }
    
    @GetMapping("/test-count")
    public String testCount() {
        return "Total products: " + productRepository.count();
    }
}
