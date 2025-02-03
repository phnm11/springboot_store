package com.nam.storespring.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "review")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
    @Id
    @Column(name = "review_id")
    String id;

    @Column(name = "user_id")
    String userId;

    @Column(name = "product_id")
    String productId;

    int rating;

    String comment;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDateTime createdAt;

    private String generateId() {
        return "RV" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    public Review() {
        this.id = generateId();
    }

}
