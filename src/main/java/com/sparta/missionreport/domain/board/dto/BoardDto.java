package com.sparta.missionreport.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.entity.BoardWorker;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.global.enums.Color;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardDto {

    @Getter
    public static class CreateBoardRequest {

        @NotBlank
        private String name;

        public Board toEntity(User createdBy) {
            return Board.builder().name(name).createdBy(createdBy).build();
        }
    }

    @Getter
    public static class UpdateBoardRequest {

        private String name;
        private Color color;
        private String description;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime deadLine;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;
        private String name;
        private String description;
        private String color;
        private String createdBy;
        private List<String> boardWorkers;
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