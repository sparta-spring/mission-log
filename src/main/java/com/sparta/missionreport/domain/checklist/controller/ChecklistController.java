package com.sparta.missionreport.domain.checklist.controller;

import com.sparta.missionreport.domain.checklist.dto.ChecklistDto;
import com.sparta.missionreport.domain.checklist.service.ChecklistService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import com.sparta.missionreport.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "5. CheckList Controller", description = "CheckList Controller")
@SecurityRequirement(name = "Bearer Authentication")
public class ChecklistController {

    private final ChecklistService checklistService;

    @Operation(summary = "체크리스트 생성")
    @PostMapping("/cards/{card_id}/checklists")
    public ResponseEntity<CommonResponseDto> createChecklist(
            @PathVariable Long card_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ChecklistDto.CreateChecklistRequest request) {

        ChecklistDto.ChecklistResponse response =
                checklistService.createChecklist(card_id, userDetails.getUser(), request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "체크리스트 생성 성공", response)
        );
    }

    @Operation(summary = "체크리스트 내용 변경")
    @PatchMapping("/cards/{card_id}/checklists/{checklist_id}/content")
    public ResponseEntity<CommonResponseDto> updateContent(
            @PathVariable Long card_id,
            @PathVariable Long checklist_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ChecklistDto.UpdateChecklistRequest request) {

        ChecklistDto.ChecklistResponse response =
                checklistService.updateContent(card_id, checklist_id, userDetails.getUser(),
                        request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "체크리스트 내용 변경 성공", response)
        );
    }

    @Operation(summary = "체크리스트 체크 여부 변경")
    @PatchMapping("/cards/{card_id}/checklists/{checklist_id}/check")
    public ResponseEntity<CommonResponseDto> updateCheck(
            @PathVariable Long card_id,
            @PathVariable Long checklist_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ChecklistDto.ChecklistResponse response =
                checklistService.updateCheck(card_id, checklist_id, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "체크리스트 체크 변경 성공", response)
        );
    }

    @Operation(summary = "체크리스트 순서 변경")
    @PatchMapping("/cards/{card_id}/checklists/{checklist_id}/sequence")
    public ResponseEntity<CommonResponseDto> updateSequence(
            @PathVariable Long card_id,
            @PathVariable Long checklist_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ChecklistDto.UpdateChecklistSequenceRequest request) {

        ChecklistDto.ChecklistResponse response =
                checklistService.updateSequence(card_id, checklist_id, userDetails.getUser(),
                        request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "체크리스트 순서 변경 성공", response)
        );
    }

    @Operation(summary = "체크리스트 삭제")
    @PatchMapping("/cards/{card_id}/checklists/{checklist_id}/delete")
    public ResponseEntity<CommonResponseDto> deleteChecklist(
            @PathVariable Long card_id,
            @PathVariable Long checklist_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        checklistService.deleteChecklist(card_id, checklist_id, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "체크리스트 삭제 성공", null)
        );
    }

    @Operation(summary = "체크리스트 전체 조회")
    @GetMapping("/cards/{card_id}/checklists")
    public ResponseEntity<CommonResponseDto> getChecklist(
            @PathVariable Long card_id,
            @PathVariable Long checklist_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ChecklistDto.ChecklistResponse response =
                checklistService.getChecklist(card_id, checklist_id, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "체크리스트 조회 성공", response)
        );
    }

}
