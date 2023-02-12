package com.catchmind.catchtable.dto.network.response;

import com.catchmind.catchtable.dto.BistroDetailDto;
import com.catchmind.catchtable.dto.PhotoDto;

public record ShopListResponse(
        String avgScore,
        Long revCnt,
        String resaBisName,
        BistroDetailDto bistroDetailDto,
        PhotoDto photoDto,
        boolean isSaved
) {
}
