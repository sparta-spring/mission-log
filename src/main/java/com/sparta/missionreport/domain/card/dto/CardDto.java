package com.sparta.missionreport.domain.card.dto;

import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.entity.CardWorker;
import com.sparta.missionreport.domain.checklist.entity.Checklist;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.comment.entity.Comment;
import com.sparta.missionreport.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CardDto {

    public static class Request {
        @NotBlank
        private String name;

        public Card toEntity(long sequence, Columns columns, User createdBy) {
            return Card.builder()
                    .name(name)
                    .sequence(sequence)
                    .createdBy(createdBy)
                    .columns(columns)
                    .build();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;
        private String name;
        private String description;
        private String color;
        private Long sequence;
        private String createdBy;
        private String columns;
        private List<String> cardWorkers;
        private List<String> comments;
        private List<String> checkLists;
        private LocalDateTime createdAt;


        public static Response of(Card card) {
            return Response.builder()
                    .id(card.getId())
                    .name(card.getName())
                    .description(card.getDescription())
                    .color(card.getColor().getColor())
                    .sequence(card.getSequence())
                    .createdBy(card.getCreatedBy().getEmail())
                    .columns(card.getColumns().getName())
                    .cardWorkers(card.getCardWorkerList().stream().map(CardWorker::getUserEmail).toList())
                    .comments(card.getCommentList().stream().map(Comment::getContent).toList())
                    .checkLists(card.getChecklistList().stream().map(Checklist::getContent).toList())
                    .createdAt(card.getCreatedAt())
                    .build();
        }
    }

}
