package com.nam.storespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nam.storespring.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

}
