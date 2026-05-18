package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String orderNumber;
    private LocalDateTime orderDate;
    private Double subtotal;
    private Double shippingCost;
    private Double tax;
    private Double total;
    private String paymentMethod;
    private String orderStatus;
    private String paymentStatus;
    private String trackingNumber;
    private LocalDateTime deliveryDate;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address shippingAddress;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();
    
    public Order() {
        this.orderNumber = "ORD" + System.currentTimeMillis();
        this.orderDate = LocalDateTime.now();
        this.orderStatus = "PENDING";
        this.paymentStatus = "PENDING";
    }
    
    // Getters
    public Long getId() { return id; }
    public String getOrderNumber() { return orderNumber; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public Double getSubtotal() { return subtotal; }
    public Double getShippingCost() { return shippingCost; }
    public Double getTax() { return tax; }
    public Double getTotal() { return total; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getOrderStatus() { return orderStatus; }
    public String getPaymentStatus() { return paymentStatus; }
    public String getTrackingNumber() { return trackingNumber; }
    public LocalDateTime getDeliveryDate() { return deliveryDate; }
    public User getUser() { return user; }
    public Address getShippingAddress() { return shippingAddress; }
    public List<OrderItem> getItems() { return items; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    public void setShippingCost(Double shippingCost) { this.shippingCost = shippingCost; }
    public void setTax(Double tax) { this.tax = tax; }
    public void setTotal(Double total) { this.total = total; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public void setDeliveryDate(LocalDateTime deliveryDate) { this.deliveryDate = deliveryDate; }
    public void setUser(User user) { this.user = user; }
    public void setShippingAddress(Address shippingAddress) { this.shippingAddress = shippingAddress; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
