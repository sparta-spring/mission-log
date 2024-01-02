package com.sparta.missionreport.domain.column.controller;

import com.sparta.missionreport.domain.column.dto.ColumnsRequestDto;
import com.sparta.missionreport.domain.column.dto.ColumnsResponseDto;
import com.sparta.missionreport.domain.column.service.ColumnsService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import com.sparta.missionreport.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "3. Column Controller", description = "Column Controller")
@SecurityRequirement(name = "Bearer Authentication")
public class ColumnsController {

    private final ColumnsService columnsService;

    @Operation(summary = "칼럼 추가")
    @PostMapping("/boards/{boardId}/columns")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> addColumn(
            @RequestBody ColumnsRequestDto.AddColumnRequestDto requestDto,
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnsResponseDto columnsResponseDto = columnsService.addColumn(requestDto, boardId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponseDto(HttpStatus.CREATED.value()
                        , "칼럼 생성 성공"
                        , columnsResponseDto));
    }

    @Operation(summary = "칼럼 이름 변경")
    @PatchMapping("/columns/{column_id}/name")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> updateColumnName(
            @RequestBody ColumnsRequestDto.UpdateColumnNameRequestDto requestDto,
            @PathVariable Long column_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnsResponseDto columnsResponseDto = columnsService.updateColumnName(requestDto,
                column_id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "칼럼 이름 수정 성공", columnsResponseDto));
    }


    @Operation(summary = "칼럼 색상 변경")
    @PatchMapping("/columns/{column_id}/color")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> updateColumnColor(
            @RequestBody ColumnsRequestDto.UpdateColumnColorRequestDto requestDto,
            @PathVariable Long column_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnsResponseDto columnsResponseDto = columnsService.updateColumnColor(requestDto,
                column_id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value()
                        , "칼럼 색상 수정 성공"
                        , columnsResponseDto));
    }


    @Operation(summary = "칼럼 순서 변경")
    @PatchMapping("/columns/{column_id}/sequence")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> updateColumnSequence(
            @RequestBody ColumnsRequestDto.UpdateColumnSequenceRequestDto requestDto,
            @PathVariable Long column_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnsResponseDto columnsResponseDto = columnsService.updateColumnSequence(requestDto,
                column_id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value()
                        , "칼럼 순서 수정 성공"
                        , columnsResponseDto));
    }


    @Operation(summary = "칼럼 삭제")
    @PatchMapping("/columns/{column_id}")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> deleteColumnSequence(
            @PathVariable Long column_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnsResponseDto columnsResponseDto = columnsService.deleteColumnSequence(column_id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "칼럼 삭제 성공", columnsResponseDto));
    }


    @Operation(summary = "칼럼 전체 조회")
    @GetMapping("/boards/{board_id}/columns")
    public ResponseEntity<CommonResponseDto<List<ColumnsResponseDto>>> getColumnList(
            @PathVariable Long board_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ColumnsResponseDto> columnsResponseDtoList = columnsService.getColumnList(board_id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value()
                        , "칼럼 조회 성공"
                        , columnsResponseDtoList));
    }


    @Operation(summary = "칼럼 단일 조회")
    @GetMapping("/columns/{column_id}")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> getColumn(
            @PathVariable Long column_id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnsResponseDto columnsResponseDto = columnsService.getColumn(column_id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value()
                        , "해당 칼럼 조회 성공"
                        , columnsResponseDto));
    }
}
