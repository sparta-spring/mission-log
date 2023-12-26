package com.sparta.missionreport.domain.column.entity;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.global.entity.CommonEntity;
import com.sparta.missionreport.global.enums.Color;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@NoArgsConstructor
@Table(name = "columns")
public class Columns extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Color color;

    @Column
    private Long sequence;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "columns")
    private List<Card> cardList = new ArrayList<>();

}
