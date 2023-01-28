package com.catchmind.catchtable.domain;

import com.catchmind.catchtable.domain.type.ReservationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

//@Table(name = "reserve")
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)
public class Reserve extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resIdx;    // 예약 번호
    @ManyToOne(optional = false)
    @JoinColumn(name = "resa_bis_name")
    private ResAdmin resAdmin;   // 식당 이름 FK
//    private String resaBisName;
    @ManyToOne(optional = false)
    @JoinColumn(name = "bis_idx")
    private BistroInfo bistroInfo;  // 식당 번호 FK bis_idx
//    private Long bisIdx;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pr_idx")
    private Profile profile;    // 예약 번호 FK pr_idx
//    private Long prIdx;
    private String prName;  // 예약자 이름
    private String resHp;   // 예약자 전화번호
    private String resRequest;  // 예약 요청사항
    private String visitName;   // 방문자 이름
    private String visitHp; // 방문자 전화번호
    private String resMonth; // 예약 달
    private String resDay;   // 예약 일
    private String resReason;   // 취소 사유
    private int resPerson;  // 예약 인원수
    private String resTime; // 예약 시간
    @Enumerated(EnumType.STRING)
    private ReservationType resStatus;  // 예약 상태
    @ManyToOne(optional = false)
    @JoinColumn(name = "bd_idx")
    private BistroDetail bistroDetail;  // 예약 상세 정보 Fk bd_idx

    protected Reserve() {}

//    public Reserve(Long resIdx, String resaBisName, Long bisIdx, Long prIdx, String prName, String resHp, String resRequest, String visitName, String visitHp, String resDate, int resPerson, String resTime, ReservationType resStatus) {
//        this.resIdx = resIdx;
//        this.resaBisName = resaBisName;
//        this.bisIdx = bisIdx;
//        this.prIdx = prIdx;
//        this.prName = prName;
//        this.resHp = resHp;
//        this.resRequest = resRequest;
//        this.visitName = visitName;
//        this.visitHp = visitHp;
//        this.resDate = resDate;
//        this.resPerson = resPerson;
//        this.resTime = resTime;
//        this.resStatus = resStatus;
//    }


    public Reserve(Long resIdx, ResAdmin resAdmin, BistroInfo bistroInfo, Profile profile, String prName, String resHp, String resRequest, String visitName, String visitHp, String resMonth, String resDay, String resReason, int resPerson, String resTime, ReservationType resStatus, BistroDetail bistroDetail) {
        this.resIdx = resIdx;
        this.resAdmin = resAdmin;
        this.bistroInfo = bistroInfo;
        this.profile = profile;
        this.prName = prName;
        this.resHp = resHp;
        this.resRequest = resRequest;
        this.visitName = visitName;
        this.visitHp = visitHp;
        this.resMonth = resMonth;
        this.resDay = resDay;
        this.resReason = resReason;
        this.resPerson = resPerson;
        this.resTime = resTime;
        this.resStatus = resStatus;
        this.bistroDetail = bistroDetail;
    }
}
