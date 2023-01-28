package com.catchmind.catchtable.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Getter
@ToString(callSuper = true)
public class ResAdmin extends AuditingField{

    private String resaUserid;  // 식당 관리자 아이디
    private String resaUserpw;  // 식당 관리자 비밀번호
    private String resaName;    // 식당 관리자 이름
    private String resaHp;  // 식당 관리자 전화번호
    private String resaRegion;  // 식당 지역
    @Id
    private String resaBisName; // 식당 이름

    protected ResAdmin(){}

    public ResAdmin(String resaUserid, String resaUserpw, String resaName, String resaHp, String resaRegion, String resaBisName) {
        this.resaUserid = resaUserid;
        this.resaUserpw = resaUserpw;
        this.resaName = resaName;
        this.resaHp = resaHp;
        this.resaRegion = resaRegion;
        this.resaBisName = resaBisName;
    }
}
