package com.catchmind.catchtable.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

//@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@ToString(callSuper = true)
public class BistroDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bdIdx;
    private String bdNotice;
    private String bdPark;
    private String bdAddr;
    private String bdHp;
    private String bdIntro;
    private String bdCaution;
    private String bdHour;
    private String bdHoliday;
    private String bdHome;
    @ManyToOne(optional = false)
    @JoinColumn(name = "resa_bis_name")
    private ResAdmin resAdmin;
    @ManyToOne(optional = false)
    @JoinColumn(name = "bis_idx")
    private BistroInfo bistroInfo;
}
