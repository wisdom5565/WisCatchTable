package com.catchmind.catchtable.dto.network.response;

import com.catchmind.catchtable.dto.BistroInfoDto;
import com.catchmind.catchtable.dto.PhotoDto;

import java.util.List;

public record ShopReviewResponse(
        double avgScore,
        Long reviewCnt
) {
}
