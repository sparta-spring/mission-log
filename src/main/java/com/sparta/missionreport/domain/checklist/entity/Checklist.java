package com.sparta.missionreport.domain.checklist.entity;

import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.checklist.dto.ChecklistDto;
import com.sparta.missionreport.global.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "checklists")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Checklist extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long sequence;

    @Column(nullable = false)
    private Boolean isChecked;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    public void update(ChecklistDto.UpdateChecklistRequest updateRequest) {
        if (updateRequest.getContent() != null) {
            this.content = updateRequest.getContent();
        }
    }

    public void updateIsChecked() {
        this.isChecked = !this.isChecked;
    }

    public void deleteChecklist() {
        this.isDeleted = true;
    }
}
