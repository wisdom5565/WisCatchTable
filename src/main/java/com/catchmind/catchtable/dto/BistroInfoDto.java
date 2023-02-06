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
        LocalDateTime regDate,
        PhotoDto photoDto
) {
    public static BistroInfoDto of(Long bisIdx,ResAdminDto resAdminDto, String bisDesc,String bisCategory,String bisRegion,String bisLunch,String bisDinner,LocalDateTime regDate,PhotoDto photoDto){
        return new BistroInfoDto(bisIdx,resAdminDto,bisDesc,bisCategory,bisRegion,bisLunch,bisDinner,regDate,photoDto);
    }
    public static BistroInfoDto from(BistroInfo bistroInfo){
        return new BistroInfoDto(
                bistroInfo.getBisIdx(),
                ResAdminDto.from(bistroInfo.getResAdmin()),
                bistroInfo.getBisDesc(),
                bistroInfo.getBisCategory(),
                bistroInfo.getBisRegion(),
                bistroInfo.getBisLunch(),
                bistroInfo.getBisDinner(),
                bistroInfo.getRegDate(),
                PhotoDto.from(bistroInfo.getPhoto())
//                bistroInfo.getBisConvenience(),
        );
    }
}
