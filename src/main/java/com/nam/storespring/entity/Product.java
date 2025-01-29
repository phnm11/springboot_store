package com.nam.storespring.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @Column(name = "product_id")
    private String id;
    private String name;
    private String description;
    private float price;
    private int stock;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at")
    @CurrentTimestamp
    private Date createdAt;

    private int views;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    private String generateId() {
        return "SP" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    public Product() {
        this.id = generateId();
    }

}
