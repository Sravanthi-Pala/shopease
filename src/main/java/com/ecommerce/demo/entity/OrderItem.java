package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer quantity;
    private BigDecimal price;
    private String productName;
    private String productImage;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    public OrderItem() {}
    
    // Getters
    public Long getId() { return id; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public String getProductName() { return productName; }
    public String getProductImage() { return productImage; }
    public Order getOrder() { return order; }
    public Product getProduct() { return product; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setProductImage(String productImage) { this.productImage = productImage; }
    public void setOrder(Order order) { this.order = order; }
    public void setProduct(Product product) { this.product = product; }
}
