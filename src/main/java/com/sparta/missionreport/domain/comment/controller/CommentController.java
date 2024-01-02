package com.sparta.missionreport.domain.comment.controller;

import com.sparta.missionreport.domain.comment.dto.CommentDto;
import com.sparta.missionreport.domain.comment.service.CommentService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import com.sparta.missionreport.global.security.UserDetailsImpl;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "6. Comment Controller", description = "Comment Controller")
@SecurityRequirement(name = "Bearer Authentication")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/cards/{card_id}/comments")
    public ResponseEntity<CommonResponseDto> createComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long card_id,
            @RequestBody @Valid CommentDto.CreateCommentRequest request
    ) {
        CommentDto.CommentResponse response = commentService.createComment(userDetails.getUser(),
                card_id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponseDto(HttpStatus.CREATED.value(), "댓글 작성 성공", response)
        );
    }

    @PatchMapping("/comments/{comment_id}/update")
    public ResponseEntity<CommonResponseDto> updateComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long comment_id,
            @RequestBody @Valid CommentDto.UpdateCommentRequest request
    ) {
        CommentDto.CommentResponse response = commentService.updateComment(userDetails.getUser(),
                comment_id, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "댓글 수정 성공", response)
        );
    }

    @PatchMapping("/comments/{comment_id}")
    public ResponseEntity<CommonResponseDto> deleteComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long comment_id
    ) {
        commentService.deleteComment(userDetails.getUser(), comment_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "댓글 삭제 성공", null)
        );
    }

    @GetMapping("/cards/{card_id}/comments")
    public ResponseEntity<CommonResponseDto> getCommentsInCard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long card_id
    ) {
        List<CommentDto.CommentResponse> responses = commentService.getCommentsInCard(
                userDetails.getUser(), card_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "댓글 리스트 조회 성공", responses)
        );
    }

    @GetMapping("/comments/{comment_id}")
    public ResponseEntity<CommonResponseDto> getComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long comment_id
    ) {
        CommentDto.CommentResponse response = commentService.getComment(userDetails.getUser(),
                comment_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "댓글 단건 조회 성공", response)
        );
    }

}
