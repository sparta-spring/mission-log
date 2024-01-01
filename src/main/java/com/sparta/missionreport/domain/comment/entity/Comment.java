package com.sparta.missionreport.domain.comment.entity;

import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.comment.dto.CommentDto;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.global.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column
    @Builder.Default
    private boolean isDeleted = false;

    public void update(CommentDto.UpdateCommentRequest request) {
        this.content = request.getContent();
    }

    public void delete() {
        this.isDeleted = true;
    }
}
