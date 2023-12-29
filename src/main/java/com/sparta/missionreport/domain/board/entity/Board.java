package com.sparta.missionreport.domain.board.entity;

import com.sparta.missionreport.domain.board.dto.BoardDto;
import com.sparta.missionreport.domain.card.dto.CardDto;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.global.entity.CommonEntity;
import com.sparta.missionreport.global.enums.Color;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "boards")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends CommonEntity {

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
    private List<BoardWorker> boardWorkerList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
    private List<Columns> columnsList = new ArrayList<>();

    public void update(BoardDto.UpdateRequest updateRequest) {
        if (updateRequest.getName() != null) this.name = updateRequest.getName();
        if (updateRequest.getColor() != null) this.color = updateRequest.getColor();
        if (updateRequest.getDescription() != null) this.description = updateRequest.getDescription();
    }
}
