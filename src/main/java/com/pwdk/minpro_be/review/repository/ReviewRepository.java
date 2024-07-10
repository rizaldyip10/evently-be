package com.pwdk.minpro_be.review.repository;

import com.pwdk.minpro_be.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEventIdAndDeletedAtIsNull(Long eventId);
}
