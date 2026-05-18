package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist_items")
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String sessionId;
    private LocalDateTime addedAt;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public WishlistItem() {
        this.addedAt = LocalDateTime.now();
    }
    
    // Getters
    public Long getId() { return id; }
    public String getSessionId() { return sessionId; }
    public LocalDateTime getAddedAt() { return addedAt; }
    public Product getProduct() { return product; }
    public User getUser() { return user; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
    public void setProduct(Product product) { this.product = product; }
    public void setUser(User user) { this.user = user; }
}
