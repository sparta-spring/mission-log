package com.sparta.missionreport.domain.board.controller;

import com.sparta.missionreport.domain.board.dto.BoardDto;
import com.sparta.missionreport.domain.board.dto.BoardDto.Response;
import com.sparta.missionreport.domain.board.dto.BoardWorkerRequestDto;
import com.sparta.missionreport.domain.board.service.BoardService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import com.sparta.missionreport.global.security.UserDetailsImpl;
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
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity<CommonResponseDto> createboard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid BoardDto.CreateRequest createRequest) {
        BoardDto.Response response = boardService.createBoard(userDetails.getUser(), createRequest);
        return ResponseEntity.status(
                        HttpStatus.CREATED)
                .body(new CommonResponseDto(HttpStatus.CONTINUE.value(), "보드가 작성되었습니다.", response));
    }

    @PatchMapping("/boards/{board_id}/name")
    public ResponseEntity<CommonResponseDto> updateName(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long board_id,
            @RequestBody @Valid BoardDto.UpdateRequest updateRequest) {
        BoardDto.Response response = boardService.updateBoardName(userDetails.getUser(), board_id,
                updateRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto<>(HttpStatus.OK.value(), "보드가 수정되었습니다.", response));
    }

    @PatchMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> updateColor(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long board_id,
            @RequestBody @Valid BoardDto.UpdateRequest updateRequest) {
        BoardDto.Response response = boardService.updateBoardColor(userDetails.getUser(), board_id,
                updateRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto<>(HttpStatus.OK.value(), "보드가 수정되었습니다.", response));
    }

    @PatchMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> updateDescription(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long board_id,
            @RequestBody @Valid BoardDto.UpdateRequest updateRequest) {
        BoardDto.Response response = boardService.updateBoardDescription(userDetails.getUser(),
                board_id, updateRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto<>(HttpStatus.OK.value(), "보드가 수정되었습니다.", response));
    }

    @DeleteMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> deleteBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long board_id) {
        boardService.deleteBoard(userDetails.getUser(), board_id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "보드가 삭제되었습니다.", null));
    }

    @GetMapping("/boards/")
    public ResponseEntity<CommonResponseDto> getBoards(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<Response> responses = boardService.getBoards(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "보드를 전제 조회 하였습니다.", responses));
    }

    @GetMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> getBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long board_id) {
        BoardDto.Response response = boardService.getBoard(userDetails.getUser(), board_id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "보드를 단일 조회 하였습니다.", response));
    }

    @PostMapping("/boards/{board_id}")
    public ResponseEntity<CommonResponseDto> inviteNewUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long board_id,
            @RequestBody @Valid BoardWorkerRequestDto requestDto) {
        boardService.inviteNewUser(userDetails.getUser(), board_id, requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponseDto(HttpStatus.OK.value(), "보드에 새로운 사용자를 초대하였습니다.", null));
    }
}