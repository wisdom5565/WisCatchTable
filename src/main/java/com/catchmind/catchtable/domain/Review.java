package com.catchmind.catchtable.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)
@Table(name = "review")
public class Review extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rev_idx")
    private Long id;
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "pr_idx")
//    private Profile profile;
    private Long revLike;
    private String revContent;
    private double revScore;
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "resa_bis_name")
//    private ResAdmin resAdmin;

    private String orgNm;

    private String savedNm;

    private String savedPath;
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // 날짜 포맷
//    @CreatedDate    //해당 엔티티가 생성될 때, 생성하는 시각을 자동으로 삽입해준다.
//    @Column(updatable = false)
//    private LocalDateTime regDate; // 생성일시
//
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    @LastModifiedDate      // 마지막 수정날짜 자동으로 생성
//    @Column(nullable = false)
//    private LocalDateTime updateDate; // 수정일시

    protected Review(){}

//    @Builder
//    public Review(Long id, Integer revLike, String revContent, double revScore, String orgNm, String savedNm, String savedPath) {
//        this.id = id;
////        this.profile = profile;
//        this.revLike = revLike;
//        this.revContent = revContent;
//        this.revScore = revScore;
////        this.resAdmin = resAdmin;
//        this.orgNm = orgNm;
//        this.savedNm = savedNm;
//        this.savedPath = savedPath;
//    }

    @Builder
    public Review(Long id, Long revLike, String revContent, double revScore, String orgNm, String savedNm, String savedPath) {
        this.id = id;
        this.revLike = revLike;
        this.revContent = revContent;
        this.revScore = revScore;
        this.orgNm = orgNm;
        this.savedNm = savedNm;
        this.savedPath = savedPath;
    }
}
