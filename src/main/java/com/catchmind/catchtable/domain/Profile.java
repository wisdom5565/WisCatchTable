package com.catchmind.catchtable.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)
public class Profile extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prIdx;
    private String prNick;
    private String prName;
    private String prIntro;
    private String prRegion;
    private String prHp;
    private String prUserpw;
    private String prGender;
    private String prBirth;
    private String prMemo;
    private int prReview;
    private int prNoshow;
    private boolean prBlock;
    private int prPoint;

    public Profile(){}

    public Profile(Long prIdx, String prNick, String prName, String prIntro, String prRegion, String prHp, String prUserpw, String prGender, String prBirth, String prMemo, int prReview, int prNoshow, boolean prBlock, int prPoint) {
        this.prIdx = prIdx;
        this.prNick = prNick;
        this.prName = prName;
        this.prIntro = prIntro;
        this.prRegion = prRegion;
        this.prHp = prHp;
        this.prUserpw = prUserpw;
        this.prGender = prGender;
        this.prBirth = prBirth;
        this.prMemo = prMemo;
        this.prReview = prReview;
        this.prNoshow = prNoshow;
        this.prBlock = prBlock;
        this.prPoint = prPoint;
    }
}
