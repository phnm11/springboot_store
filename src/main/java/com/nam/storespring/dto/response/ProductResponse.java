package com.nam.storespring.dto.response;

import java.sql.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String id;
    String name;
    String description;
    float price;
    int stock;

    String categoryId;

    String imageUrl;

    Date createdAt;

    int views;

    List<ReviewResponse> reviews;
}
