package com.sparta.missionreport.domain.card.entity;

import com.sparta.missionreport.domain.card.dto.CardDto;
import com.sparta.missionreport.domain.checklist.entity.Checklist;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.comment.entity.Comment;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.global.entity.CommonEntity;
import com.sparta.missionreport.global.enums.Color;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private Color color = Color.NONE;

    @Column
    private Long sequence;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deadLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "columns_id")
    private Columns columns;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<CardWorker> cardWorkerList = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Checklist> checklistList = new ArrayList<>();

    public void updateName(CardDto.NameRequest nameRequest) {
        this.name = nameRequest.getName();
    }

    public void updateColor(CardDto.ColorRequest colorRequest) {
        this.color = colorRequest.getColor();
    }

    public void updateDescription(CardDto.DescriptionRequest descriptionRequest) {
        this.description = descriptionRequest.getDescription();
    }

    public void updateDeadLine(CardDto.DeadLineRequest deadLineRequest) {
        this.deadLine = deadLineRequest.getDeadLine();
    }
}
