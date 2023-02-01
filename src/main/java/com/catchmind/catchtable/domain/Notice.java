package com.catchmind.catchtable.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
public class Notice extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noIdx;             // 공지사항 번호
    private String noTitle;         // 공지사항 제목
    @ManyToOne(optional = false)
    @JoinColumn(name = "ad_idx")   // ad_idx로 관리자이름 찾기
    private Admin admin;            // 관리자 이름(CatchTable로 고정)
    private String noContent;       // 공지사항 내용

    protected Notice() {}

    public Notice(String noTitle, Admin admin, String noContent) {
        this.noTitle = noTitle;
        this.admin = admin;
        this.noContent = noContent;
    }

    public static Notice of(String noTitle, Admin admin, String noContent){
        return new Notice(noTitle, admin, noContent);
    }

    @Override
    public int hashCode() { return Objects.hash(noIdx); }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Notice notice)) return false;
        return noIdx != null && noIdx.equals(notice.noIdx);
    }
}
