package com.catchmind.catchtable.dto;

import com.catchmind.catchtable.domain.ReviewHeart;

public record ReviewHeartDto(
        Long heIdx,
        ProfileDto profileDto,
        ReviewDto reviewDto
) {
    public static ReviewHeartDto from(ReviewHeart heart) {
        return new ReviewHeartDto(
                heart.getHeIdx(),
                ProfileDto.from(heart.getProfile()),
                ReviewDto.from(heart.getReview())
        );
    }
}
