package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer discountPercent;
    private String discountText;
    private String category;
    private String brand;
    private Double rating;
    private Integer ratingCount;
    private Integer stockQuantity;
    private String imageUrl;
    private Boolean featured = false;
    private Boolean bestSeller = false;
    private Boolean active = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public Product() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getOriginalPrice() { return originalPrice; }
    public Integer getDiscountPercent() { return discountPercent; }
    public String getDiscountText() { return discountText; }
    public String getCategory() { return category; }
    public String getBrand() { return brand; }
    public Double getRating() { return rating; }
    public Integer getRatingCount() { return ratingCount; }
    public Integer getStockQuantity() { return stockQuantity; }
    public String getImageUrl() { return imageUrl; }
    public Boolean getFeatured() { return featured; }
    public Boolean getBestSeller() { return bestSeller; }
    public Boolean getActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setOriginalPrice(BigDecimal originalPrice) { this.originalPrice = originalPrice; }
    public void setDiscountPercent(Integer discountPercent) { this.discountPercent = discountPercent; }
    public void setDiscountText(String discountText) { this.discountText = discountText; }
    public void setCategory(String category) { this.category = category; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setRatingCount(Integer ratingCount) { this.ratingCount = ratingCount; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setFeatured(Boolean featured) { this.featured = featured; }
    public void setBestSeller(Boolean bestSeller) { this.bestSeller = bestSeller; }
    public void setActive(Boolean active) { this.active = active; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public BigDecimal getFinalPrice() {
        if (discountPercent != null && discountPercent > 0) {
            return price.multiply(BigDecimal.valueOf(100 - discountPercent)).divide(BigDecimal.valueOf(100));
        }
        return price;
    }
}
