package com.ecommerce.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @Column(length = 1000)
    private String message;
    
    private String type;
    private boolean isRead;
    private LocalDateTime createdAt;
    private String link;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public Notification() {
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }
    
    public Notification(String title, String message, String type, User user) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
