package com.nam.storespring.mapper;

import org.mapstruct.Mapper;

import com.nam.storespring.dto.request.ReviewRequest;
import com.nam.storespring.dto.response.ReviewResponse;
import com.nam.storespring.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toReview(ReviewRequest request);

    ReviewResponse toReviewResponse(Review review);
}
