package com.crackint.Shop.model;

import jakarta.persistence.*;

@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private double price;
  private String image;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user; // ✅ Add this line

  public Product() {}

  public Product(String name, String description, double price, String image) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.image = image;
  }

  // --- Getters and Setters ---

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public double getPrice() {
    return price;
  }
  public void setPrice(double price) {
    this.price = price;
  }
  public String getImage() {
    return image;
  }
  public void setImage(String image) {
    this.image = image;
  }

  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
}
