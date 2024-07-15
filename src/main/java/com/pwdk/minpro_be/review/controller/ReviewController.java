package com.pwdk.minpro_be.review.controller;

import com.pwdk.minpro_be.auth.helpers.Claims;
import com.pwdk.minpro_be.responses.Response;
import com.pwdk.minpro_be.review.dto.ReviewRequestDto;
import com.pwdk.minpro_be.review.service.ReviewService;
import lombok.extern.java.Log;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@Log

public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{eventSlug}")
    public ResponseEntity<?> getEventReviews(
            @PathVariable("eventSlug") String eventSlug,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return Response.success(
                "Event Review Fetched",
                reviewService.getEventReviews(eventSlug, pageable));
    }

    @PostMapping("/{eventSlug}")
    public ResponseEntity<?> createUserReview(@PathVariable("eventSlug") String eventSlug,
                                              @RequestBody ReviewRequestDto requestDto)
    {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        return Response.success(HttpStatus.CREATED.value(), "Created", reviewService.createUserReview(requestDto, eventSlug, email));
    }

    @PutMapping("/user-review/{reviewId}")
    public ResponseEntity<?> updateUserReview(
            @PathVariable("reviewId") Long reviewId,
            @RequestBody ReviewRequestDto requestDto)
    {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        SecurityContext ctx = SecurityContextHolder.getContext();
        log.info("Security Context -> " + ctx.toString());

        return Response.success("Update success", reviewService.updateUserReview(requestDto, reviewId, email));
    }

    @DeleteMapping("/user-review/{reviewId}")
    public ResponseEntity<?> deleteUserReview(@PathVariable("reviewId") Long reviewId) {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        reviewService.deleteUserReview(reviewId, email);

        return Response.success("Delete Success");
    }
}
