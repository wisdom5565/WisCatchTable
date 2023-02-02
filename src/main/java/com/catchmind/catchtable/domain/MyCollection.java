package com.catchmind.catchtable.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class MyCollection extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long colIdx;
    private String colTitle;
    private String colContent;
    private boolean colLock;

    @ManyToOne
    @JoinColumn(name="pr_idx")
    private Profile profile;
    private String bisIdx;


}
