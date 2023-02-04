package com.catchmind.catchtable.dto.network.response;

import com.catchmind.catchtable.dto.ProfileDto;
import com.catchmind.catchtable.dto.ResAdminDto;
import com.catchmind.catchtable.dto.ReviewPhotoDto;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewResponse(
        long revIdx,
        ProfileDto profileDto,
        String revContent,
        double revScore,
        ResAdminDto resAdminDto,
        List<ReviewPhotoDto> photo,
        Long resIdx,
        LocalDateTime regDate,
        LocalDateTime updateDate,
        Long revCom
) {
}
