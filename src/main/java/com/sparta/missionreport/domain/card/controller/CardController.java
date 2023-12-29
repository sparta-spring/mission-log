package com.sparta.missionreport.domain.card.controller;

import com.sparta.missionreport.domain.card.dto.CardDto;
import com.sparta.missionreport.domain.card.dto.CardWorkerRequestDto;
import com.sparta.missionreport.domain.card.service.CardService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import com.sparta.missionreport.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;


    @PostMapping("/columns/{column_id}/cards")
    public ResponseEntity<CommonResponseDto> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long column_id,
                                                        @RequestBody @Valid CardDto.CreateRequest createRequest
    ) {
        CardDto.Response response = cardService.createCard(userDetails.getUser(), column_id, createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponseDto(HttpStatus.CREATED.value(), "카드 생성 성공", response)
        );
    }


    @PatchMapping("/cards/{card_id}/update")
    public ResponseEntity<CommonResponseDto> update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable Long card_id,
                                                            @RequestBody @Valid CardDto.UpdateRequest updateRequest
    ) {
        CardDto.Response response = cardService.update(userDetails.getUser(), card_id, updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 수정 성공", response)
        );
    }


    @PatchMapping("/cards/{card_id}")
    public ResponseEntity<CommonResponseDto> deleteCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long card_id
    ) {
        cardService.deleteCard(userDetails.getUser(), card_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 삭제 성공", null)
        );
    }

    @GetMapping("/boards/{board_id}/cards")
    public ResponseEntity<CommonResponseDto> getCardsByBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @PathVariable Long board_id
    ) {
        List<CardDto.Response> responses = cardService.getCardsByBoard(userDetails.getUser(), board_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 전체 조회 성공", responses)
        );
    }

    @GetMapping("/cards/{card_id}")
    public ResponseEntity<CommonResponseDto> getCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long card_id
    ) {
        CardDto.Response response = cardService.getCard(userDetails.getUser(), card_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 조회 성공", response)
        );
    }

    @PostMapping("/cards/{card_id}")
    public ResponseEntity<CommonResponseDto> inviteUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long card_id,
                                                        @RequestBody @Valid CardWorkerRequestDto requestDto
    ) {
        cardService.inviteUser(userDetails.getUser(), card_id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "사용자 초대 성공", null)
        );
    }
}
