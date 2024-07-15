package com.pwdk.minpro_be.review.service.impl;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.service.EventService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.exception.DataConflictException;
import com.pwdk.minpro_be.exception.DataNotFoundException;
import com.pwdk.minpro_be.review.dto.ReviewRequestDto;
import com.pwdk.minpro_be.review.dto.ReviewResponseDto;
import com.pwdk.minpro_be.review.entity.Review;
import com.pwdk.minpro_be.review.repository.ReviewRepository;
import com.pwdk.minpro_be.review.service.ReviewService;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final EventService eventService;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             @Lazy UserService userService,
                             @Lazy EventService eventService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    public Page<ReviewResponseDto> getEventReviews(String eventSlug, Pageable pageable) {
        Event event = eventService.findBySlug(eventSlug);
        return reviewRepository.findByEventIdAndDeletedAtIsNull(event.getId(), pageable)
                .map(Review::toResponseDto);
    }

    @Override
    public Review createUserReview(ReviewRequestDto reviewDto, String eventSlug, String userEmail) {
        Event event = eventService.findBySlug(eventSlug);
        User user = userService.findByEmail(userEmail);

        if (reviewDto.getReview().isBlank() || reviewDto.getReview().isEmpty()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Please enter your review");
        }

        if (reviewDto.getRating() == 0) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Rating can be only greater than 1");
        }

        var newReview = new Review();
        newReview.setUser(user);
        newReview.setEvent(event);
        newReview.setRating(reviewDto.getRating());
        newReview.setReview(reviewDto.getReview());

        return reviewRepository.save(newReview);
    }

    @Override
    public Review updateUserReview(ReviewRequestDto reviewDto, Long reviewId, String userEmail) {
        Optional<Review> updatedReview = reviewRepository.findById(reviewId);
        User user = userService.findByEmail(userEmail);

        if (updatedReview.isEmpty()) {
            throw new DataNotFoundException("Review not found");
        }

        if (updatedReview.get().getDeletedAt() != null) {
            throw new DataConflictException("You cannot update deleted review");
        }

        if (!Objects.equals(updatedReview.get().getUser().getId(), user.getId())) {
            throw new DataConflictException("User ID did not match. You cannot update this review");
        }

        updatedReview.get().setReview(reviewDto.getReview());
        updatedReview.get().setRating(reviewDto.getRating());
        return reviewRepository.save(updatedReview.get());
    }

    @Override
    public void deleteUserReview(Long reviewId, String userEmail) {
        Optional<Review> deletedReview = reviewRepository.findById(reviewId);
        User user = userService.findByEmail(userEmail);

        if (deletedReview.isEmpty()) {
            throw new DataNotFoundException("Review not found");
        }

        if (deletedReview.get().getDeletedAt() != null) {
            throw new DataConflictException("Review already deleted");
        }

        if (!Objects.equals(deletedReview.get().getUser().getId(), user.getId())) {
            throw new DataConflictException("User ID did not match. You cannot delete this review");
        }
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.systemDefault();

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, zoneId);

        deletedReview.get().setDeletedAt(zonedDateTime.toInstant());

        reviewRepository.save(deletedReview.get());
    }
}
