package com.catchmind.catchtable.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)
public class Comment extends AuditingField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comIdx;                    // 댓글 번호
    @ManyToOne(optional = false)
    @JoinColumn(name = "pr_idx")            // pr_idx로 찾기
    private Profile profile;                // 댓글 작성자 닉네임       // 회원 정보 테이블의 닉네임
    private String comContent;              // 댓글 내용
    @ManyToOne(optional = false)
    @JoinColumn(name = "rev_idx")
    private Review review;                  // 리뷰 번호

    protected Comment() {}

    public Comment(Long comIdx, Profile profile, String comContent, Review review) {
        this.comIdx = comIdx;
        this.profile = profile;
        this.comContent = comContent;
        this.review = review;
    }
}
