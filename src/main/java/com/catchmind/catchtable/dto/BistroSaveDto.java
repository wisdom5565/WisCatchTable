package com.catchmind.catchtable.dto;

import com.catchmind.catchtable.domain.BistroInfo;
import com.catchmind.catchtable.domain.BistroSave;
import com.catchmind.catchtable.domain.Profile;
import com.catchmind.catchtable.domain.ResAdmin;

import java.time.LocalDateTime;

public record BistroSaveDto(
        Long saveIdx,
        ResAdminDto resAdminDto,
        ProfileDto profileDto,
        BistroDetailDto bistroDetailDto,
        Long colIdx

) {
    public static BistroSaveDto from(BistroSave entity){
        return new BistroSaveDto(
                entity.getSaveIdx(),
                ResAdminDto.from(entity.getResAdmin()),
                ProfileDto.from(entity.getProfile()),
                BistroDetailDto.from(entity.getBistroDetail()),
                entity.getColIdx()
        );
    }


}
