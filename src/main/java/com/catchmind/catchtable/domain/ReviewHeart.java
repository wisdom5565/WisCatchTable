package com.catchmind.catchtable.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ReviewHeart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heIdx;
    @ManyToOne
    @JoinColumn(name="pr_idx")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name="rev_idx")
    private Review review;
}
