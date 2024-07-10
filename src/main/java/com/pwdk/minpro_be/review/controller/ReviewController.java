package com.pwdk.minpro_be.review.controller;

import com.pwdk.minpro_be.auth.helpers.Claims;
import com.pwdk.minpro_be.review.dto.ReviewRequestDto;
import com.pwdk.minpro_be.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{eventSlug}")
    public ResponseEntity<?> getEventReviews(@PathVariable("eventSlug") String eventSlug) {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getEventReviews(eventSlug));
    }

    @PostMapping("/{eventSlug}")
    public ResponseEntity<?> createUserReview(@PathVariable("eventSlug") String eventSlug,
                                              @RequestBody ReviewRequestDto requestDto)
    {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createUserReview(requestDto, eventSlug, email));
    }

    @PutMapping("/user-review/{reviewId}")
    public ResponseEntity<?> updateUserReview(@PathVariable("reviewId") Long reviewId,
                                              @RequestBody ReviewRequestDto requestDto)
    {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        reviewService.updateUserReview(requestDto, reviewId, email);

        return ResponseEntity.status(HttpStatus.OK).body(reviewService);
    }

    @DeleteMapping("/user-review/{reviewId}")
    public ResponseEntity<?> deleteUserReview(@PathVariable("reviewId") Long reviewId) {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        reviewService.deleteUserReview(reviewId, email);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully delete review");
    }
}
