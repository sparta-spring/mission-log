package com.sparta.missionreport.domain.comment.dto;

import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.comment.entity.Comment;
import com.sparta.missionreport.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

public class CommentDto {

    @Schema(description = "댓글 생성 요청 dto")
    @Getter
    public static class CreateCommentRequest {

        @Schema(description = "댓글 내용", defaultValue = "퍼가요~^^")
        @NotBlank
        private String content;

        public Comment toEntity(User user, Card card) {
            return Comment.builder()
                    .content(content)
                    .createdBy(user)
                    .card(card)
                    .build();
        }
    }

    @Schema(description = "댓글 수정 요청 dto")
    @Getter
    public static class UpdateCommentRequest {

        @Schema(description = "댓글 내용", defaultValue = "언제 하시나요 ~^^")
        @NotBlank
        private String content;
    }


    @Schema(description = "댓글 응답 정보 dto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponse {

        @Schema(description = "댓글 id", defaultValue = "1L")
        private Long id;

        @Schema(description = "댓글이 달리 카드 id", defaultValue = "Doing")
        private Long cardId;

        @Schema(description = "댓글 내용", defaultValue = "퍼가요~^^")
        private String content;

        @Schema(description = "댓글 작성자", defaultValue = "sparta@gmail.com")
        private String createdBy;

        @Schema(description = "카드 생성 일자", defaultValue = "2023-01-01T00:00:00")
        private LocalDateTime createdAt;

        @Schema(description = "카드 수정 일자", defaultValue = "2023-01-01T19:00:00")
        private LocalDateTime modifiedAt;


        public static CommentResponse of(Comment comment) {
            return CommentResponse.builder()
                    .id(comment.getId())
                    .cardId(comment.getCard().getId())
                    .content(comment.getContent())
                    .createdBy(comment.getCreatedBy().getEmail())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build();
        }

    }


}
