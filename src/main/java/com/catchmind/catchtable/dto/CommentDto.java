package com.catchmind.catchtable.dto;

import com.catchmind.catchtable.domain.Comment;

import java.time.LocalDateTime;

public record CommentDto(
        Long comIdx,
        ProfileDto profileDto,
        String comContent,
        ReviewDto reviewDto,
        LocalDateTime regDate
) {
    public static CommentDto from(Comment comment){
        return new CommentDto(
                comment.getComIdx(),
                ProfileDto.from(comment.getProfile()),
                comment.getComContent(),
                ReviewDto.from(comment.getReview()),
                comment.getRegDate()
        );
    }
}
