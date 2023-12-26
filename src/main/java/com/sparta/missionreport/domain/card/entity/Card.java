package com.sparta.missionreport.domain.card.entity;

import com.sparta.missionreport.domain.checklist.entity.Checklist;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.comment.entity.Comment;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.global.entity.CommonEntity;
import com.sparta.missionreport.global.enums.Color;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cards")
@NoArgsConstructor
public class Card extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private Color color;

    @Column
    private Long sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "columns_id")
    private Columns columns;

    @OneToMany(mappedBy = "card")
    private List<CardWorker> cardWorkerList = new ArrayList<>();

    @OneToMany(mappedBy = "card")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "card")
    private List<Checklist> checklistList = new ArrayList<>();

}
