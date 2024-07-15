package com.pwdk.minpro_be.review.service;

import com.pwdk.minpro_be.review.dto.ReviewRequestDto;
import com.pwdk.minpro_be.review.dto.ReviewResponseDto;
import com.pwdk.minpro_be.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    Page<ReviewResponseDto> getEventReviews(String eventSlug, Pageable pageable);

    Review createUserReview(ReviewRequestDto reviewDto, String eventSlug, String userEmail);

    Review updateUserReview(ReviewRequestDto reviewDto, Long reviewId, String userEmail);

    void deleteUserReview(Long reviewId, String userEmail);
}
