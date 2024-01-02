package com.sparta.missionreport.domain.board.dto;

import com.sparta.missionreport.domain.board.entity.BoardWorker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;


@Schema(name = "보드 작업자 dto")
@Getter
public class BoardWorkerDto {

    @Getter
    @Builder
    @Schema(name = "보드 작업자 초대 요청 dto")
    public static class BoardWorkerInviteRequest {

        @Schema(description = "초대할 유저 이메일", example = "sparta@gmail.com")
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
        private String email;
    }

    @Schema(name = "보드 작업자 정보 응답 dto")
    @Builder
    @Getter
    public static class BoardWorkerResponse {

        @Schema(description = "보드 작업자 id", example = "1L")
        private Long id;

        @Schema(description = "보드 id", example = "1L")
        private Long boardId;

        @Schema(description = "사용자 email", example = "sparta@gmail.com")
        private String worker;


        public static BoardWorkerResponse of(BoardWorker boardWorker) {
            return BoardWorkerResponse.builder()
                    .id(boardWorker.getId())
                    .boardId(boardWorker.getBoard().getId())
                    .worker(boardWorker.getUserEmail())
                    .build();
        }
    }
}