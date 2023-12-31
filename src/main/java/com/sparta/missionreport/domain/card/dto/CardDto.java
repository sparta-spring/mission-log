package com.sparta.missionreport.domain.card.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.entity.CardWorker;
import com.sparta.missionreport.domain.checklist.entity.Checklist;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.comment.entity.Comment;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.global.enums.Color;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CardDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카드 생성 요청 dto")
    public static class CreateCardRequest {

        @NotBlank
        @Schema(description = "카드 이름", defaultValue = "개발 전체 진행 완료하자")
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

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카드 수정 요청 dto")
    public static class UpdateCardRequest {

        @Schema(description = "카드 이름", defaultValue = "아니다... 월요일까지 해볼까")
        private String name;

        @Schema(description = "카드 색상", defaultValue = "red")
        private Color color;

        @Schema(description = "카드 설명", defaultValue = "카드, 댓글 기능 구현 완료하기")
        private String description;

        @Schema(description = "카드 마감 일자", defaultValue = "2023-01-03 23:59")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime deadLine;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "동일 컬럼 내 순서 이동 요청 dto")
    public static class MoveCardInSameColRequest {

        @Schema(description = "카드 순서", defaultValue = "3L")
        @NotNull
        private Long sequence;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "다른 컬럼으로 이동 요청 dto")
    public static class MoveCardRequest {

        @Schema(description = "이동할 컬럼 id", defaultValue = "2L")
        @NotNull
        private Long column_id;

        @Schema(description = "카드 순서", defaultValue = "3L")
        @NotNull
        private Long sequence;
    }



    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "카드 정보 반환 dto")
    public static class CardResponse {

        @Schema(description = "카드 id", defaultValue = "1L")
        private Long id;

        @Schema(description = "컬럼 id", defaultValue = "1L")
        private Long columnsId;

        @Schema(description = "카드 이름", defaultValue = "아니다... 월요일까지 해볼까")
        private String name;

        @Schema(description = "카드 설명", defaultValue = "카드, 댓글 기능 구현 완료하기")
        private String description;

        @Schema(description = "카드 색상", example = "red")
        private String color;

        @Schema(description = "카드 순서", example = "1L")
        private Long sequence;

        @Schema(description = "생성 유저 이메일", defaultValue = "sparta@gmail.com")
        private String createdBy;

        @Schema(description = "카드 마감 일자", example = "2023-12-29 23:59")
        private LocalDateTime deadLine;

        @Schema(description = "카드 작업자 리스트", defaultValue = "[\"spsta@gmail.com\", \"worker1@gmail.com\",\"worker2@gmail.com\"]")
        private List<String> cardWorkers;

        @Schema(description = "카드 댓글 리스트", defaultValue = "[\"화이팅\", \"꼭 성공하자\",\"주말 사수\"]")
        private List<String> comments;

        @Schema(description = "카드 체크 리스트", defaultValue = "[\"생성 기능 구현\", \"순서 변경 기능\",\"삭제 기능\"]")
        private List<String> checkLists;

        @Schema(description = "카드 생성 일자", defaultValue = "2023-01-01T00:00:00")
        private LocalDateTime createdAt;

        @Schema(description = "카드 수정 일자", defaultValue = "2023-01-01T19:11:00")
        private LocalDateTime modifiedAt;


        public static CardResponse of(Card card) {
            return CardResponse.builder()
                    .id(card.getId())
                    .columnsId(card.getColumns().getId())
                    .name(card.getName())
                    .description(card.getDescription())
                    .color(card.getColor().getColor())
                    .sequence(card.getSequence())
                    .createdBy(card.getCreatedBy().getEmail())
                    .deadLine(card.getDeadLine())
                    .cardWorkers(card.getCardWorkerList().stream().map(CardWorker::getUserEmail).toList())
                    .comments(card.getCommentList().stream().map(Comment::getContent).toList())
                    .checkLists(card.getChecklistList().stream().map(Checklist::getContent).toList())
                    .createdAt(card.getCreatedAt())
                    .modifiedAt(card.getModifiedAt())
                    .build();
        }

    }

}
