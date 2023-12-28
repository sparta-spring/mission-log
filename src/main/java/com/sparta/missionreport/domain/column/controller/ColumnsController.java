package com.sparta.missionreport.domain.column.controller;

import com.sparta.missionreport.domain.column.dto.ColumnsRequestDto;
import com.sparta.missionreport.domain.column.dto.ColumnsResponseDto;
import com.sparta.missionreport.domain.column.service.ColumnsService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import com.sparta.missionreport.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ColumnsController {

    private final ColumnsService columnsService;

    @PostMapping("/boards/{boardId}/columns")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> addColumn(@RequestBody ColumnsRequestDto.AddColumnsRequestDto requestDto,
                                                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                           @PathVariable Long boardId)
    {
        ColumnsResponseDto columnsResponseDto = columnsService.addColumn(requestDto, userDetails.getUser().getId(), boardId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new CommonResponseDto(HttpStatus.CREATED.value(), "카드 생성 성공",columnsResponseDto));
    }

}