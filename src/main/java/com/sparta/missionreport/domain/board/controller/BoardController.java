package com.sparta.missionreport.domain.board.controller;

import com.sparta.missionreport.domain.board.dto.BoardDto;
import com.sparta.missionreport.domain.board.dto.BoardDto.Response;
import com.sparta.missionreport.domain.board.dto.BoardWorkerDto;
import com.sparta.missionreport.domain.board.service.BoardService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import com.sparta.missionreport.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "2. Board Controller", description = "Board Controller")
@SecurityRequirement(name = "Bearer Authentication")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "보드 생성")
    @PostMapping("/boards")
    public ResponseEntity<CommonResponseDto> createboard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid BoardDto.CreateBoardRequest createBoardRequest) {
        BoardDto.Response response = boardService.createBoard(userDetails.getUser(),
                createBoardRequest);
        return ResponseEntity.status(
                        HttpStatus.CREATED)
                .body(new CommonResponseDto(HttpStatus.CREATED.value(), "보드가 작성되었습니다.", response));
    }

    @Operation(summary = "보드 수정 : 이름")
    @PatchMapping("/boards/{board_id}/name")
    public ResponseEntity<CommonResponseDto> updateName(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long board_id,
            @RequestBody @Valid BoardDto.UpdateBoardRequest updateRequest) {
        BoardDto.Response response = boardService.updateBoardName(userDetails.getUser(), board_id,
                updateRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto<>(HttpStatus.OK.value(), "보드가 수정되었습니다.", response));
    }

    @Operation(summary = "보드 수정 : 색상")
    @PatchMapping("/boards/{board_id}/color")
    public ResponseEntity<CommonResponseDto> updateColor(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long board_id,
            @RequestBody @Valid BoardDto.UpdateBoardRequest updateBoardRequest) {
        BoardDto.Response response = boardService.updateBoardColor(userDetails.getUser(), board_id,
                updateBoardRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto<>(HttpStatus.OK.value(), "보드가 수정되었습니다.", response));
    }

    @Operation(summary = "보드 수정 : 설명")
    @PatchMapping("/boards/{board_id}/description")
    public ResponseEntity<CommonResponseDto> updateDescription(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long board_id,
            @RequestBody @Valid BoardDto.UpdateBoardRequest updateBoardRequest) {
        BoardDto.Response response = boardService.updateBoardDescription(userDetails.getUser(),
                board_id, updateBoardRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto<>(HttpStatus.OK.value(), "보드가 수정되었습니다.", response));
    }

    @Operation(summary = "보드 삭제")
    @PatchMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> deleteBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long board_id) {
        boardService.deleteBoard(userDetails.getUser(), board_id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "보드가 삭제되었습니다.", null));
    }

    @Operation(summary = "보드 전체 조회")
    @GetMapping("/boards/")
    public ResponseEntity<CommonResponseDto> getBoards(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Response> responses = boardService.getBoards(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "보드를 전제 조회 하였습니다.", responses));
    }

    @Operation(summary = "보드 단일 조회")
    @GetMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> getBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long board_id) {
        BoardDto.Response response = boardService.getBoard(userDetails.getUser(), board_id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "보드를 단일 조회 하였습니다.", response));
    }

    @Operation(summary = "보드에 작업자 초대")
    @PostMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> inviteNewUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long board_id,
            @RequestBody @Valid BoardWorkerDto.BoardWorkerInviteRequest requestDto) {
        boardService.inviteNewUser(userDetails.getUser(), board_id, requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "보드에 새로운 사용자를 초대하였습니다.", null));
    }

    @Operation(summary = "보드내 작업자 삭제")
    @PatchMapping("/cards/{board_id}/workers")
    public ResponseEntity<CommonResponseDto> deleteBoardWorker(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long board_id
    ) {
        boardService.deleteBoardWorker(userDetails.getUser(), board_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto<>(HttpStatus.OK.value(), "작업자 삭제 성공", null)
        );
    }

    @Operation(summary = "보드내 작업자 리스트 조회")
    @GetMapping("/boards/{board_id}/workers")
    public ResponseEntity<CommonResponseDto> getBoardsInBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long board_id) {
        List<BoardWorkerDto.BoardWorkerResponse> response = boardService.getWorkersInBoard(
                userDetails.getUser(), board_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "보드 작업자 리스트를 조회하였습니다.", response));
    }

    @Operation(summary = "보드내 작업자 단일 조회")
    @GetMapping("/boards/{board_id}/workers/search")
    public ResponseEntity<CommonResponseDto> searchWorkerInBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long board_id,
            @RequestParam(name = "email", required = true) String email
    ) {
        BoardWorkerDto.BoardWorkerResponse response = boardService.searchWorkerInBoard(
                userDetails.getUser(), board_id, email);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "보드 작업자를 검색하였습니다.", response));
    }
}