package com.nam.storespring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nam.storespring.dto.request.ReviewRequest;
import com.nam.storespring.dto.response.ReviewResponse;
import com.nam.storespring.entity.Review;
import com.nam.storespring.mapper.ReviewMapper;
import com.nam.storespring.repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    public ReviewResponse createReview(ReviewRequest request) {
        Review review = new Review();

        review.setUserId(request.getUserId());
        review.setProductId(request.getProductId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }

    public ReviewResponse updateReview(String id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        review.setComment(request.getComment());

        return reviewMapper.toReviewResponse(reviewRepository.save(review));

    }

    public void deleteReview(String reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
