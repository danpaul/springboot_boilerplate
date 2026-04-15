//package com.example.demo.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "products")
//public class Product {
//
//    // 🔑 Primary key
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // 📌 Basic column with constraints
//    @Column(nullable = false, length = 100)
//    @NotBlank(message = "Title cannot be empty")
//    private String title;
//
//    // 📝 Longer text field
//    @Column(columnDefinition = "TEXT")
//    private String description;
//
//    // 💰 Money field (best type for prices)
//    @NotNull
//    @DecimalMin("0.0")
//    private BigDecimal price;
//
//    // 📦 Stock quantity
//    @Min(0)
//    private int stock;
//
//    // 📅 Automatically stored timestamps
//    @Column(updatable = false)
//    private LocalDateTime createdAt;
//
//    private LocalDateTime updatedAt;
//
//    // 🔗 Example relationship (many products belong to one category)
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;
//
//    // 🔄 Auto-set timestamps
//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//        updatedAt = createdAt;
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }
//
//    // 📦 Getters & Setters
//    public Long getId() { return id; }
//
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public BigDecimal getPrice() { return price; }
//    public void setPrice(BigDecimal price) { this.price = price; }
//
//    public int getStock() { return stock; }
//    public void setStock(int stock) { this.stock = stock; }
//
//    public LocalDateTime getCreatedAt() { return createdAt; }
//
//    public LocalDateTime getUpdatedAt() { return updatedAt; }
//
//    public Category getCategory() { return category; }
//    public void setCategory(Category category) { this.category = category; }
//}