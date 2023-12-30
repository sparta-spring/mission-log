package com.sparta.missionreport.domain.user.entity;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.entity.BoardWorker;
import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.entity.CardWorker;
import com.sparta.missionreport.domain.comment.entity.Comment;
import com.sparta.missionreport.domain.user.enums.UserRole;
import com.sparta.missionreport.global.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 500)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "user")
    private List<BoardWorker> boardWorkerList = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CardWorker> cardWorkerList = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy")
    private List<Card> cardList = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy")
    private List<Comment> commentList = new ArrayList<>();

    public void updateIsDeleted() {
        this.isDeleted = !this.isDeleted;
    }

}
