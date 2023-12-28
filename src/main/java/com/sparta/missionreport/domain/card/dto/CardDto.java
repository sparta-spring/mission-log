package com.sparta.missionreport.domain.card.dto;

import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.entity.CardWorker;
import com.sparta.missionreport.domain.checklist.entity.Checklist;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.comment.entity.Comment;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.global.enums.Color;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CardDto {

    @Getter
    public static class Request {
        @NotBlank
        private String name;

        public Card toEntity(long sequence, Columns columns, User createdBy) {
            return Card.builder()
                    .name(name)
                    .sequence(sequence)
                    .color(Color.NONE)
                    .createdBy(createdBy)
                    .columns(columns)
                    .build();
        }
    }

    @Getter
    public static class NameRequest {
        @NotBlank
        private String name;
    }

    @Getter
    public static class ColorRequest {
        @NotBlank
        private Color color;
    }

    @Getter
    public static class DescriptionRequest {

        @NotBlank
        private String description;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;
        private Long columnsId;
        private String name;
        private String description;
        private String color;
        private Long sequence;
        private String createdBy;
        private List<String> cardWorkers;
        private List<String> comments;
        private List<String> checkLists;
        private LocalDateTime createdAt;


        public static Response of(Card card) {
            return Response.builder()
                    .id(card.getId())
                    .columnsId(card.getColumns().getId())
                    .name(card.getName())
                    .description(card.getDescription())
                    .color(card.getColor().getColor())
                    .sequence(card.getSequence())
                    .createdBy(card.getCreatedBy().getEmail())
                    .cardWorkers(card.getCardWorkerList().stream().map(CardWorker::getUserEmail).toList())
                    .comments(card.getCommentList().stream().map(Comment::getContent).toList())
                    .checkLists(card.getChecklistList().stream().map(Checklist::getContent).toList())
                    .createdAt(card.getCreatedAt())
                    .build();
        }
    }

}