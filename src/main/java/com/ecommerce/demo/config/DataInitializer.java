package com.ecommerce.demo.config;

import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.User;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Create admin and demo user
        if (!userRepository.existsByEmail("admin@shopease.com")) {
            User admin = new User("admin@shopease.com", "admin123", "Admin User", "9999999999", "ADMIN");
            User demoUser = new User("user@shopease.com", "user123", "Demo User", "9876543210", "USER");
            userRepository.save(admin);
            userRepository.save(demoUser);
            System.out.println("? Users created");
        }
        
        // Load products only if empty
        if (productRepository.count() == 0) {
            // Fashion (5)
            saveProduct("Men Cotton T-Shirt", "Premium cotton t-shirt", 499, 1299, 61, "Fashion", "Roadster", 4.3, 2341, "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=300", true, true);
            saveProduct("Women Floral Top", "Beautiful floral top", 699, 1999, 65, "Fashion", "Here&Now", 4.4, 1876, "https://images.unsplash.com/photo-1515372039744-b8f02a3ae446?w=300", true, false);
            saveProduct("Men Slim Jeans", "Stretchable slim jeans", 999, 2999, 66, "Fashion", "Levis", 4.5, 3456, "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=300", true, true);
            saveProduct("Women Denim Jacket", "Classic denim jacket", 1499, 4999, 70, "Fashion", "Zara", 4.6, 1234, "https://images.pexels.com/photos/2065200/pexels-photo-2065200.jpeg?w=300", false, false);
            saveProduct("Men Formal Shirt", "Cotton formal shirt", 799, 2499, 68, "Fashion", "Arrow", 4.4, 987, "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=300", false, false);
            
            // Footwear (3)
            saveProduct("Nike Free RN 2018 Red", "Red Nike Free RN 2018", 1999, 4999, 60, "Footwear", "Nike", 4.6, 2345, "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=300", true, true);
            saveProduct("Nike Air Force 1 Shadow", "Pastel color AF1 Shadow", 1799, 4499, 60, "Footwear", "Nike", 4.5, 1876, "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=300", false, false);
            saveProduct("Women Heels", "Party wear heels", 999, 2999, 66, "Footwear", "Bata", 4.3, 1234, "https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=300", false, false);
            
            // Electronics (4)
            saveProduct("iPhone 15", "Latest iPhone", 71900, 79900, 10, "Electronics", "Apple", 4.8, 1245, "https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=300", true, true);
            saveProduct("Sony Headphones", "Noise cancellation", 29990, 39990, 25, "Electronics", "Sony", 4.7, 892, "https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?w=300", true, false);
            saveProduct("MacBook Pro", "Powerful laptop", 169900, 189900, 10, "Electronics", "Apple", 4.9, 2341, "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=300", true, true);
            saveProduct("Samsung Galaxy S21", "S21 Ultra", 52999, 105999, 50, "Electronics", "Samsung", 4.7, 543, "https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=300", false, false);
            
            // Home Decor (3)
            saveProduct("Wall Painting", "Canvas painting", 1499, 4999, 70, "Home Decor", "Art Street", 4.6, 1234, "https://images.unsplash.com/photo-1513519245088-0e12902e5a38?w=300", false, false);
            saveProduct("Wall Clock", "Modern wall clock", 1299, 3999, 67, "Home Decor", "Ajanta", 4.6, 2345, "https://images.unsplash.com/photo-1563861826100-9cb868fdbe1c?w=300", false, false);
            saveProduct("Cushion Cover Set", "Set of 4 covers", 599, 1999, 70, "Home Decor", "Home Centre", 4.4, 1876, "https://images.unsplash.com/photo-1616486338812-3dadae4b4ace?w=300", false, false);
            
            // Jewellery (3)
            saveProduct("Gold Necklace", "American diamond necklace", 2499, 7999, 68, "Jewellery", "PC Jeweller", 4.6, 1234, "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?w=300", false, true);
            saveProduct("Golden Earrings", "Golden earrings", 899, 2999, 70, "Jewellery", "Caratlane", 4.5, 2345, "https://images.unsplash.com/photo-1617038220319-276d3cfab638?w=300", false, false);
            saveProduct("Silver Anklet", "Anklet with charms", 299, 899, 66, "Jewellery", "GIVA", 4.4, 654, "https://images.unsplash.com/photo-1617038220319-276d3cfab638?w=300", false, false);
            
            // Accessories (4)
            saveProduct("Men Watch", "Analog watch", 2999, 9999, 70, "Accessories", "Fastrack", 4.7, 3456, "https://images.unsplash.com/photo-1524805444758-089113d48a6d?w=300", false, true);
            saveProduct("Women Watch", "Rose gold watch", 2499, 4999, 50, "Accessories", "Titan", 4.6, 2341, "https://images.pexels.com/photos/190819/pexels-photo-190819.jpeg?w=300", false, false);
            saveProduct("Designer Handbag", "Sling bag", 1499, 4999, 70, "Accessories", "Lavie", 4.7, 2345, "https://images.unsplash.com/photo-1591561954557-26941169b49e?w=300", false, true);
            saveProduct("Leather Wallet", "Leather wallet", 599, 1999, 70, "Accessories", "Wildhorn", 4.4, 1876, "https://images.unsplash.com/photo-1622560480605-d83c853bc5c3?w=300", false, false);
            
            // Beauty (3)
            saveProduct("Lakme Lipstick", "Matte finish", 499, 999, 50, "Beauty", "Lakme", 4.5, 2341, "https://images.unsplash.com/photo-1586495777744-4413f21062fa?w=300", false, true);
            saveProduct("Face Wash", "Face wash", 199, 499, 60, "Beauty", "Loreal", 4.3, 4567, "https://images.unsplash.com/photo-1556228578-0d85b1a4d571?w=300", false, false);
            saveProduct("Hair Mask", "Hair mask", 249, 599, 58, "Beauty", "Biotique", 4.5, 2345, "https://images.unsplash.com/photo-1608248597279-f99d160bfcbc?w=300", false, false);
            
            System.out.println("? 25 Products loaded into MySQL!");
        }
    }
    
    private void saveProduct(String name, String description, double price, double originalPrice, int discount,
                             String category, String brand, double rating, int ratingCount, String imageUrl,
                             boolean featured, boolean bestSeller) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(BigDecimal.valueOf(price));
        product.setOriginalPrice(BigDecimal.valueOf(originalPrice));
        product.setDiscountPercent(discount);
        product.setDiscountText(discount + "% off");
        product.setCategory(category);
        product.setBrand(brand);
        product.setRating(rating);
        product.setRatingCount(ratingCount);
        product.setImageUrl(imageUrl);
        product.setFeatured(featured);
        product.setBestSeller(bestSeller);
        product.setActive(true);
        productRepository.save(product);
    }
}
