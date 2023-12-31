package com.sparta.missionreport.domain.card.controller;

import com.sparta.missionreport.domain.card.dto.CardDto;
import com.sparta.missionreport.domain.card.dto.CardWorkerDto;
import com.sparta.missionreport.domain.card.service.CardService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import com.sparta.missionreport.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "4. Card Controller", description = "Card Controller")
@SecurityRequirement(name = "Bearer Authentication")
public class CardController {

    private final CardService cardService;

    @Operation(summary = "카드 생성")
    @PostMapping("/columns/{column_id}/cards")
    public ResponseEntity<CommonResponseDto> createCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long column_id,
            @RequestBody @Valid CardDto.CreateCardRequest createCardRequest
    ) {
        CardDto.CardResponse response = cardService.createCard(userDetails.getUser(), column_id,
                createCardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponseDto(HttpStatus.CREATED.value(), "카드 생성 성공", response)
        );
    }

    @Operation(summary = "카드 수정")
    @PatchMapping("/cards/{card_id}/update")
    public ResponseEntity<CommonResponseDto> update(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long card_id,
            @RequestBody @Valid CardDto.UpdateCardRequest updateCardRequest
    ) {
        CardDto.CardResponse response = cardService.update(userDetails.getUser(), card_id,
                updateCardRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 수정 성공", response)
        );
    }


    @Operation(summary = "카드 삭제")
    @PatchMapping("/cards/{card_id}")
    public ResponseEntity<CommonResponseDto> deleteCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long card_id
    ) {
        cardService.deleteCard(userDetails.getUser(), card_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 삭제 성공", null)
        );
    }

    @Operation(summary = "해당 보드 내의 카드 조회")
    @GetMapping("/boards/{board_id}/cards")
    public ResponseEntity<CommonResponseDto> getCardsByBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long board_id
    ) {
        List<CardDto.CardResponse> responses = cardService.getCardsByBoard(userDetails.getUser(),
                board_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 전체 조회 성공", responses)
        );
    }

    @Operation(summary = "해당 컬럼 내의 카드 조회")
    @GetMapping("/columns/{columns_id}/cards")
    public ResponseEntity<CommonResponseDto> getCardsByColumn(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long columns_id
    ) {
        List<CardDto.CardResponse> responses = cardService.getCardsByColumn(userDetails.getUser(),
                columns_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "컬럼 내의 카드 전체 조회 성공", responses)
        );
    }

    @Operation(summary = "카드 단건 조회")
    @GetMapping("/cards/{card_id}")
    public ResponseEntity<CommonResponseDto> getCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long card_id
    ) {
        CardDto.CardResponse response = cardService.getCard(userDetails.getUser(), card_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 조회 성공", response)
        );
    }

    @Operation(summary = "해당 카드에 작업자 초대")
    @PostMapping("/cards/{card_id}/workers")
    public ResponseEntity<CommonResponseDto> inviteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long card_id,
            @RequestBody @Valid CardWorkerDto.CardWorkerInviteRequest requestDto
    ) {
        cardService.inviteUser(userDetails.getUser(), card_id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "사용자 초대 성공", null)
        );
    }

    @Operation(summary = "해당 카드에 작업자 삭제")
    @PatchMapping("/cards/{card_id}/workers")
    public ResponseEntity<CommonResponseDto> deleteWorker(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long card_id,
            @RequestParam String email
    ) {
        cardService.deleteWorker(userDetails.getUser(), card_id, email);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "작업자 삭제 성공", null)
        );
    }

    @Operation(summary = "해당 카드의 작업자 리스트 조회")
    @GetMapping("/cards/{card_id}/workers")
    public ResponseEntity<CommonResponseDto> getWorkersInCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long card_id
    ) {
        List<CardWorkerDto.CardWorkerResponse> response = cardService.getWorkersInCard(
                userDetails.getUser(), card_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 작업자 리스트 조회 성공", response)
        );
    }

    @Operation(summary = "작업자 검색")
    @GetMapping("/cards/{card_id}/workers/search")
    public ResponseEntity<CommonResponseDto> searchWorkerInCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long card_id,
            @RequestParam(name = "email", required = true) String email
    ) {
        CardWorkerDto.CardWorkerResponse response = cardService.searchWorkerInCard(
                userDetails.getUser(), card_id, email);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 작업자 검색 성공", response)
        );
    }

    @Operation(summary = "동일 컬럼 내 카드 이동")
    @PatchMapping("/cards/{card_id}/sequence")
    public ResponseEntity<CommonResponseDto> moveCardInSameColumn(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long card_id,
            @RequestBody @Valid CardDto.MoveCardInSameColRequest request
    ) {
        CardDto.CardResponse response = cardService.moveCardInSameColumn(userDetails.getUser(),
                card_id, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 이동 성공", response)
        );
    }

    @Operation(summary = "다른 컬럼으로 이동")
    @PatchMapping("/cards/{card_id}/columns/sequence")
    public ResponseEntity<CommonResponseDto> moveCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long card_id,
            @RequestBody @Valid CardDto.MoveCardRequest request
    ) {
        CardDto.CardResponse response = cardService.moveCard(userDetails.getUser(), card_id,
                request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "카드 이동 성공", response)
        );
    }
}
