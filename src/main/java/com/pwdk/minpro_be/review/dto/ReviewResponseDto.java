package com.pwdk.minpro_be.review.dto;

import com.pwdk.minpro_be.users.dto.UserDto;
import lombok.Data;

import java.time.Instant;

@Data
public class ReviewResponseDto {
    private Long id;
    private UserDto user;
    private String review;
    private Integer rating;
    private Instant createdAt;
}
