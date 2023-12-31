package com.sparta.missionreport.domain.card.dto;

import com.sparta.missionreport.domain.card.entity.CardWorker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(name = "카드 작업자 dto")
public class CardWorkerDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "카드 작업자 초대 요청 dto")
    public static class CardWorkerInviteRequest {

        @Schema(description = "초대할 유저 이메일", defaultValue = "anotheruser@gmail.com")
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
        private String email;
    }


    @Schema(name = "카드 작업자 정보 응답 dto")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardWorkerResponse {

        @Schema(description = "카드 작업자 id", defaultValue = "1L")
        private Long id;

        @Schema(description = "카드 id", defaultValue = "1L")
        private Long cardId;

        @Schema(description = "사용자 email", defaultValue = "sparta@gmail.com")
        private String worker;


        public static CardWorkerResponse of(CardWorker cardWorker) {
            return CardWorkerResponse.builder()
                    .id(cardWorker.getId())
                    .cardId(cardWorker.getCard().getId())
                    .worker(cardWorker.getUserEmail())
                    .build();
        }
    }
}
