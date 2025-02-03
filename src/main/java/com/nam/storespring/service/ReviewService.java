package com.nam.storespring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nam.storespring.dto.request.ReviewRequest;
import com.nam.storespring.dto.response.ReviewResponse;
import com.nam.storespring.entity.Review;
import com.nam.storespring.mapper.ReviewMapper;
import com.nam.storespring.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewService {
    private ReviewRepository reviewRepository;

    private ReviewMapper reviewMapper;

    private AuthenticationService authenticationService;

    public ReviewService(AuthenticationService authenticationService, ReviewRepository reviewRepository,
            ReviewMapper reviewMapper) {
        this.authenticationService = authenticationService;
        this.reviewMapper = reviewMapper;
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    public ReviewResponse createReview(ReviewRequest request) {
        Review review = new Review();

        review.setUserId(authenticationService.getCurrentUserId());
        review.setProductId(request.getProductId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        log.info("User ID from token: {}", authenticationService.getCurrentUserId());

        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }

    public ReviewResponse updateReview(String id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        String currentUserId = authenticationService.getCurrentUserId();

        if (!review.getUserId().equals(currentUserId)) {
            throw new RuntimeException("You do not have permission to update this review");
        }

        review.setComment(request.getComment());

        return reviewMapper.toReviewResponse(reviewRepository.save(review));

    }

    public void deleteReview(String reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        String currentUserId = authenticationService.getCurrentUserId();

        if (!review.getUserId().equals(currentUserId)
                && !currentUserId.equals("813d9125-b8c7-46ac-8eb6-148a2bcf02c4")) {
            throw new RuntimeException("You do not have permission to delete this review");
        }
        reviewRepository.deleteById(reviewId);
    }
}
