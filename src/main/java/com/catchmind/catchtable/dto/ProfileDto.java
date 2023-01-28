package com.catchmind.catchtable.dto;

import com.catchmind.catchtable.domain.Profile;

import java.time.LocalDateTime;

public record ProfileDto(
        Long prIdx,
        String prNick,
        String prName,
        String prIntro,
        String prRegion,
        String prHp,
        String prUserpw,
        String prGender,
        String prBirth,
        String prMemo,
        LocalDateTime regDate,
        LocalDateTime updateDate,
        int prReview,
        int prNoshow,
        boolean prBlock,
        int prPoint

) {
    public static ProfileDto of(Long prIdx, String prNick, String prName, String prIntro, String prRegion, String prHp, String prUserpw, String prGender, String prBirth, String prMemo, int prReview, int prNoshow, boolean prBlock, int prPoint){
        return new ProfileDto(prIdx, prNick, prName, prIntro, prRegion, prHp, prUserpw, prGender, prBirth, prMemo, null, null, prReview, prNoshow, prBlock, prPoint);
    }

    public static ProfileDto of(Long prIdx, String prNick, String prName, String prIntro, String prRegion, String prHp, String prUserpw, String prGender, String prBirth, String prMemo, LocalDateTime regDate, LocalDateTime updateDate, int prReview, int prNoshow, boolean prBlock, int prPoint){
        return new ProfileDto(prIdx, prNick, prName, prIntro, prRegion, prHp, prUserpw, prGender, prBirth, prMemo, regDate, updateDate, prReview, prNoshow, prBlock,prPoint);
    }

    public static ProfileDto from(Profile entity){
        return new ProfileDto(
                entity.getPrIdx(),
                entity.getPrNick(),
                entity.getPrName(),
                entity.getPrIntro(),
                entity.getPrRegion(),
                entity.getPrHp(),
                entity.getPrUserpw(),
                entity.getPrGender(),
                entity.getPrBirth(),
                entity.getPrMemo(),
                entity.getRegDate(),
                entity.getUpdateDate(),
                entity.getPrReview(),
                entity.getPrNoshow(),
                entity.isPrBlock(),
                entity.getPrPoint()
        );
    }


}
