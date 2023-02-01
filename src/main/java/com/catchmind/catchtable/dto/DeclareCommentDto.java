package com.catchmind.catchtable.dto;

import com.catchmind.catchtable.domain.DeclareComment;

import java.time.LocalDateTime;

public record DeclareCommentDto(
        Long decIdx,
        ReviewDto reviewDto,
        CommentDto commentDto ,     // comIdx
        String decNick ,            // decNick
        ProfileDto profileDto,      // prNick
        String decTitle,
        String decContent,          // 추가
        LocalDateTime regDate
) {
    public static DeclareCommentDto from(DeclareComment declareComment){
        return new DeclareCommentDto(
                declareComment.getDecIdx(),
                ReviewDto.from(declareComment.getReview()),
                CommentDto.from(declareComment.getComment()),
                declareComment.getDecNick(),
                ProfileDto.from(declareComment.getProfile()),
                declareComment.getDecContent(),
                declareComment.getDecTitle(),
                declareComment.getRegDate()
        );
    }
}
