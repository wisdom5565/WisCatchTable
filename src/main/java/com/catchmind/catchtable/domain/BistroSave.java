package com.catchmind.catchtable.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@ToString
@Entity
@Data
public class BistroSave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saveIdx;

    @ManyToOne(optional = false) @JoinColumn(name = "resaBisName")
    private ResAdmin resAdmin;

    @ManyToOne(optional = false) @JoinColumn(name = "prIdx")
    private Profile profile;
    @ManyToOne(optional = false) @JoinColumn(name = "bdIdx")
    private BistroDetail bistroDetail;

    protected BistroSave () {}

    @Builder
    public BistroSave(Long saveIdx, ResAdmin resAdmin, Profile profile, BistroDetail bistroDetail) {
        this.saveIdx = saveIdx;
        this.resAdmin = resAdmin;
        this.profile = profile;
        this.bistroDetail = bistroDetail;
    }


}
