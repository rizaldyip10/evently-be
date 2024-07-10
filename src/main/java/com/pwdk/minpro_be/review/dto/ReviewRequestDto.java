package com.pwdk.minpro_be.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequestDto {

    @NotNull(message = "Please enter your review")
    private String review;

    @NotNull(message = "Please enter your rating")
    private Integer rating;
}
