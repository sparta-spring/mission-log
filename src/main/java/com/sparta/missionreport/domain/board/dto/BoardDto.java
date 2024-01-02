package com.sparta.missionreport.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.entity.BoardWorker;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.global.enums.Color;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "보드 생성 요청 dto")
    public static class CreateBoardRequest {

        @NotBlank
        @Schema(description = "보드 이름", defaultValue = "개발 전체 진행 완료하자")
        private String name;

        public Board toEntity(User createdBy) {
            return Board.builder().name(name).createdBy(createdBy).build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "보드 수정 요청 dto")
    public static class UpdateBoardRequest {

        @Schema(description = "보드 이름", defaultValue = "보드 이름 수정 test")
        private String name;

        @Schema(description = "보드 색상", defaultValue = "blue")
        private Color color;

        @Schema(description = "보드 설명", defaultValue = "보드, 수정 완료하기")
        private String description;

        @Schema(description = "보드 마감 일자", defaultValue = "2023-01-03 09:00")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime deadLine;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "보드 정보 반환 dto")
    public static class Response {

        @Schema(description = "보드 id", defaultValue = "1L")
        private Long id;

        @Schema(description = "보드 이름", defaultValue = "제발...문제없이 작동해주세요")
        private String name;

        @Schema(description = "보드 설명", defaultValue = "카드, 댓글 기능 구현 완료하기")
        private String description;

        @Schema(description = "카드 색상", example = "red")
        private String color;

        @Schema(description = "생성 유저 이메일", defaultValue = "sparta@gmail.com")
        private String createdBy;

        @Schema(description = "보드 작업자 리스트", defaultValue = "[\"spsta@gmail.com\", \"worker1@gmail.com\",\"worker2@gmail.com\"]")
        private List<String> boardWorkers;

        @Schema(description = "보드 생성 일자", defaultValue = "2023-01-01T00:00:00")
        private LocalDateTime createdAt;

        public static BoardDto.Response of(Board board) {
            return Response.builder()
                    .id(board.getId())
                    .name(board.getName())
                    .description(board.getDescription())
                    .color(board.getColor().getColor())
                    .createdBy(board.getCreatedBy().getEmail())
                    .boardWorkers(board.getBoardWorkerList().stream().map(BoardWorker::getUserEmail).toList())
                    .createdAt(board.getCreatedAt())
                    .build();
        }
    }
}