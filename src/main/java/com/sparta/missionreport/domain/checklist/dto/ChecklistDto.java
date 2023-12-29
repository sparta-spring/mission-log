package com.sparta.missionreport.domain.checklist.dto;


import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.checklist.entity.Checklist;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChecklistDto {

    @Getter
    @Schema(description = "체크리스트 생성 요청 dto")
    public static class CreateRequest {

        @NotBlank
        @Schema(description = "체크리스트 내용", defaultValue = "checklist content")
        private String content;

        public Checklist toEntity(long sequence, Card card) {
            return Checklist.builder()
                    .content(content)
                    .sequence(sequence)
                    .isChecked(false)
                    .build();
        }
    }

    @Getter
    @Schema(description = "체크리스트 변경 요청 dto (순서 제외)")
    public static class UpdateRequest {

        @Schema(description = "체크리스트 내용", defaultValue = "checklist content")
        private String content;

    }

    @Getter
    @Schema(description = "체크리스트 순서 변경 요청 dto")
    public static class UpdateSequenceRequest {

        @NotBlank
        @Schema(description = "체크리스트 순서", defaultValue = "1")
        private Long sequence;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "체크리스트 응답 dto")
    public static class Response {

        @Schema(description = "체크리스트 내용", defaultValue = "checklist content")
        private String content;

        @Schema(description = "체크리스트 순서", defaultValue = "1")
        private Long sequence;

        @Schema(description = "체크 여부", defaultValue = "true")
        private Boolean isChecked;

        @Schema(description = "카드 이름", defaultValue = "true")
        private String cardName;

        public static Response of(Checklist checklist) {
            return Response.builder()
                    .content(checklist.getContent())
                    .sequence(checklist.getSequence())
                    .isChecked(checklist.getIsChecked())
                    .cardName(checklist.getCard().getName())
                    .build();
        }
    }

}
