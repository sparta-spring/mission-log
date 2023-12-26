package com.sparta.missionreport.domain.board.entity;

import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.user.entity.User;
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
@Table(name = "boards")
@NoArgsConstructor
public class Board extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String name;

    @Column(nullable = false)
    private Color color;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @OneToMany(mappedBy = "board")
    private List<BoardWorker> boardWorkerList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Columns> columnsList = new ArrayList<>();
}
