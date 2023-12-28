package com.sparta.missionreport.domain.card.controller;

import com.sparta.missionreport.domain.card.dto.CardDto;
import com.sparta.missionreport.domain.card.service.CardService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import com.sparta.missionreport.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;


    @PostMapping("/columns/{column_id}/cards")
    public ResponseEntity<CommonResponseDto> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long column_id,
                                                        @RequestBody @Valid CardDto.Request request
    ) {
        CardDto.Response response = cardService.createCard(userDetails.getUser(), column_id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponseDto(HttpStatus.CREATED.value(), "카드 생성 성공", response)
        );
    }

    @PatchMapping("/cards/{card_id}/name")
    public ResponseEntity<CommonResponseDto> updateName(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable Long card_id,
                                                            @RequestBody @Valid CardDto.NameRequest nameRequest) {
        CardDto.Response response = cardService.updateName(userDetails.getUser(), card_id, nameRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 이름 수정 성공", response)
        );
    }

    @PatchMapping("/cards/{card_id}/color")
    public ResponseEntity<CommonResponseDto> updateColor(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long card_id,
                                                        @RequestBody @Valid CardDto.ColorRequest colorRequest) {
        CardDto.Response response = cardService.updateColor(userDetails.getUser(), card_id, colorRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 색상 수정 성공", response)
        );
    }

    @PatchMapping("/cards/{card_id}/description")
    public ResponseEntity<CommonResponseDto> updateDescription(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         @PathVariable Long card_id,
                                                         @RequestBody @Valid CardDto.DescriptionRequest descriptionRequest) {
        CardDto.Response response = cardService.updateDescription(userDetails.getUser(), card_id, descriptionRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 설명 수정 성공", response)
        );
    }


}
