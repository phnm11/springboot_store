package com.nam.storespring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nam.storespring.dto.request.ReviewRequest;
import com.nam.storespring.dto.response.ReviewResponse;
import com.nam.storespring.entity.Review;
import com.nam.storespring.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getReviews() {
        return reviewService.getReviews();
    }

    @PostMapping
    public ReviewResponse createReview(@RequestBody @Valid ReviewRequest request) {
        return reviewService.createReview(request);
    }

    @PutMapping("/{reviewId}")
    public ReviewResponse updateReview(@PathVariable String reviewId, @RequestBody ReviewRequest request) {
        return reviewService.updateReview(reviewId, request);
    }

    @DeleteMapping("/{reviewId}")
    public String deleteReview(@PathVariable String reviewId) {
        reviewService.deleteReview(reviewId);

        return "Review deleted successfully.";
    }
}
