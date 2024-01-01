package com.sparta.missionreport.domain.column.entity;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.global.entity.CommonEntity;
import com.sparta.missionreport.global.enums.Color;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "columns")
public class Columns extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Color color = Color.NONE;

    @Column
    private Long sequence;

    @Column
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "columns", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<Card> cardList = new ArrayList<>();

    public void updateName(String name) {
        this.name = name;
    }

    public void updateColor(Color color) {
        this.color = color;
    }

    public void updateSequence(String flag, Long sequence) {
        if(flag.equals("+")){
            this.sequence += sequence;
        }
        else {
            this.sequence -= sequence;
        }
    }

    public void updateDelete() {
        this.isDeleted = !this.isDeleted;
    }
}
