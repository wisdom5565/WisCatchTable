package com.catchmind.catchtable.dto;
import com.catchmind.catchtable.domain.Profile;
import com.catchmind.catchtable.domain.Reserve;

import java.time.LocalDateTime;

public record ReserveDto(
        Long resIdx,
        ResAdminDto resAdminDto,
        BistroInfoDto bistroInfoDto,
        ProfileDto profileDto,
        BistroDetailDto bistroDetailDto,
        String prName,
        String resHp,
        String resRequest,
        String visitName,
        String visitHp,
        String resMonth,
        String resDay,
        String resReason,
        Long resPerson,
        String resTime,
        String resStatus,
        LocalDateTime regDate,
        LocalDateTime updateDate,
        boolean revStatus

) {

    public static ReserveDto from(Reserve reserve){
        return new ReserveDto(
                reserve.getResIdx(),
                ResAdminDto.from(reserve.getResAdmin()),
                BistroInfoDto.from(reserve.getBistroInfo()),
                ProfileDto.from(reserve.getProfile()),
                BistroDetailDto.from(reserve.getBistroDetail()),
                reserve.getPrName(),
                reserve.getResHp(),
                reserve.getResRequest(),
                reserve.getVisitName(),
                reserve.getVisitHp(),
                reserve.getResMonth(),
                reserve.getResDay(),
                reserve.getResReason(),
                reserve.getResPerson(),
                reserve.getResTime(),
                reserve.getResStatus().getDescription(),
                reserve.getRegDate(),
                reserve.getUpdateDate(),
                reserve.isRevStatus()

        );
    }

    public static ReserveDto of(Long resIdx) {
        return new ReserveDto(resIdx,null,null,null,null,null,null,null,null,null,null,null,null,0L,null,null,null,null, false);
    }

    public static ReserveDto of(boolean revStatus) {
        return new ReserveDto(0L,null,null,null,null,null,null,null,null,null,null,null,null,0L,null,null,null,null, revStatus);
    }

    public Reserve toEntity() {
        return Reserve.of(
                resIdx
        );
    }

    public Reserve toRevStatus(){
        return Reserve.of(
                revStatus
        );
    }


}
