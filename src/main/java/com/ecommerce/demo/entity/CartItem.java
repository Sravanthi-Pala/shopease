package com.ecommerce.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer quantity;
    private String sessionId;
    private String productSize;
    private String productColor;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public CartItem() {
        this.quantity = 1;
    }
    
    // Getters
    public Long getId() { return id; }
    public Integer getQuantity() { return quantity; }
    public String getSessionId() { return sessionId; }
    public String getProductSize() { return productSize; }
    public String getProductColor() { return productColor; }
    public Product getProduct() { return product; }
    public User getUser() { return user; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public void setProductSize(String productSize) { this.productSize = productSize; }
    public void setProductColor(String productColor) { this.productColor = productColor; }
    public void setProduct(Product product) { this.product = product; }
    public void setUser(User user) { this.user = user; }
}
