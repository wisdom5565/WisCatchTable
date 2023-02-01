package com.catchmind.catchtable.dto;

import com.catchmind.catchtable.domain.BistroInfo;

import java.time.LocalDateTime;

public record BistroInfoDto(
        Long bisIdx,
        ResAdminDto resAdminDto,
        String bisDesc,
        String bisCategory,
        String bisRegion,
        String bisLunch,
        String bisDinner,
//        String bisConvenience,
        LocalDateTime regDate
) {
    public static BistroInfoDto from(BistroInfo bistroInfo){
        return new BistroInfoDto(
                bistroInfo.getBisIdx(),
                ResAdminDto.from(bistroInfo.getResAdmin()),
                bistroInfo.getBisDesc(),
                bistroInfo.getBisCategory(),
                bistroInfo.getBisRegion(),
                bistroInfo.getBisLunch(),
                bistroInfo.getBisDinner(),
                bistroInfo.getRegDate()
//                bistroInfo.getBisConvenience(),
        );
    }
}
