package com.sparta.missionreport.domain.checklist.entity;

import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.global.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
