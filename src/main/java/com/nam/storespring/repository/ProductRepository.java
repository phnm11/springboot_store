package com.nam.storespring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nam.storespring.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.reviews WHERE p.id = :productId")
    Optional<Product> findByIdWithReviews(@Param("productId") String productId);
}
