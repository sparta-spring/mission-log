package com.sparta.missionreport.domain.checklist.entity;

import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.global.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "checklists")
@NoArgsConstructor
public class Checklist extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private Long sequence;

    @Column
    private Boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;
}
