package com.ecommerce.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String fullName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;
    private String landmark;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public Address() {}
    
    // Getters
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getAddressLine1() { return addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPincode() { return pincode; }
    public String getLandmark() { return landmark; }
    public User getUser() { return user; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setPincode(String pincode) { this.pincode = pincode; }
    public void setLandmark(String landmark) { this.landmark = landmark; }
    public void setUser(User user) { this.user = user; }
    
    public String getFullAddress() {
        return addressLine1 + (addressLine2 != null ? ", " + addressLine2 : "") + 
               (landmark != null ? ", " + landmark : "") + ", " + city + ", " + state + " - " + pincode;
    }
}
