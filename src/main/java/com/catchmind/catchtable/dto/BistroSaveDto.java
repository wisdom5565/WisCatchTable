package com.catchmind.catchtable.dto;

import com.catchmind.catchtable.domain.BistroSave;

public record BistroSaveDto(
        Long saveIdx,
        ResAdminDto resAdminDto,
        ProfileDto profileDto,
        Long prIdx

) {


    public static BistroSaveDto from(BistroSave entity){
        return new BistroSaveDto(
                entity.getSaveIdx(),
                ResAdminDto.from(entity.getResAdmin()),
                ProfileDto.from(entity.getProfile()),
                entity.getProfile().getPrIdx()
        );
    }


}
