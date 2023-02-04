package com.catchmind.catchtable.dto.network.response;

import com.catchmind.catchtable.dto.ReviewDto;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
        Long comIdx,
        String prNick,
        Long prIdx,
        String comContent,
        Long revIdx,
        LocalDateTime regDate,
        Long comLike,
        boolean isComm,
        boolean isComLike
) {
}
