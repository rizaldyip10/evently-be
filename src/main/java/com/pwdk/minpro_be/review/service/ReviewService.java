package com.pwdk.minpro_be.review.service;

import com.pwdk.minpro_be.review.dto.ReviewRequestDto;
import com.pwdk.minpro_be.review.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getEventReviews(String eventSlug);

    Review createUserReview(ReviewRequestDto reviewDto, String eventSlug, String userEmail);

    Review updateUserReview(ReviewRequestDto reviewDto, Long reviewId, String userEmail);

    void deleteUserReview(Long reviewId, String userEmail);
}
