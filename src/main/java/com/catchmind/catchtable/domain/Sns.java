package com.catchmind.catchtable.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Sns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long snsIdx;
    @ManyToOne
    @JoinColumn(name="pr_idx")
    private Profile profile;
    private String snsAddr;
    private String snsType;

    protected Sns() {}


}
