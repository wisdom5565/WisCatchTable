package com.catchmind.catchtable.domain;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)
@Table(name = "review")
public class Review extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rev_idx")
    private Long revIdx;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pr_idx")
    private Profile profile;
    private Long revLike;
    private String revContent;
    private double revScore;
    @ManyToOne(optional = false)
    @JoinColumn(name = "resa_bis_name")
    private ResAdmin resAdmin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "res_idx")
    private Reserve reserve;

    protected Review(){}

    public Review(Long id, Profile profile, Long revLike, String revContent, double revScore, ResAdmin resAdmin, Reserve reserve) {
        this.revIdx = id;
        this.profile = profile;
        this.revLike = revLike;
        this.revContent = revContent;
        this.revScore = revScore;
        this.resAdmin = resAdmin;
        this.reserve = reserve;
    }

    public Review(Profile profile, String revContent, double revScore, ResAdmin resAdmin, Reserve reserve) {
        this.profile = profile;
        this.revContent = revContent;
        this.revScore = revScore;
        this.resAdmin = resAdmin;
        this.reserve = reserve;
    }

    public Review(Long id) {
        this.revIdx = id;
    }

    public static Review of(Profile profile,String revContent, double revScore, ResAdmin resAdmin, Reserve reserve) {
        return new Review(profile,revContent,revScore,resAdmin,reserve);
    }
    public static Review of(Long revIdx) {
        return new Review(revIdx);
    }
}
