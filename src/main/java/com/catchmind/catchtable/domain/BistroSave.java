package com.catchmind.catchtable.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Entity
@Data
public class BistroSave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saveIdx;

    @ManyToOne(optional = false) @JoinColumn(name = "resa_bis_name")
    private ResAdmin resAdmin;

    @ManyToOne(optional = false) @JoinColumn(name = "pr_idx")
    private Profile profile;

    protected BistroSave() {}

    @Builder
    public BistroSave(Long saveIdx, ResAdmin resAdmin, Profile profile) {
        this.saveIdx = saveIdx;
        this.resAdmin = resAdmin;
        this.profile = profile;
    }
}
