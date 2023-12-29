package com.sparta.missionreport.domain.column.controller;

import com.sparta.missionreport.domain.column.dto.ColumnsRequestDto;
import com.sparta.missionreport.domain.column.dto.ColumnsResponseDto;
import com.sparta.missionreport.domain.column.exception.ColumnsCustomException;
import com.sparta.missionreport.domain.column.service.ColumnsService;
import com.sparta.missionreport.global.common.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Column 컨트롤러",description = "Column 관련 API입니다.")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ColumnsController {

    private final ColumnsService columnsService;

    @Operation(summary = "칼럼 추가")
    @PostMapping("/boards/{boardId}/columns")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> addColumn(@RequestBody ColumnsRequestDto.AddColumnRequestDto requestDto,
                                                                           @PathVariable Long boardId) {
        ColumnsResponseDto columnsResponseDto = columnsService.addColumn(requestDto, boardId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponseDto(HttpStatus.CREATED.value()
                        , "카드 생성 성공"
                        , columnsResponseDto));
    }
    @Operation(summary = "칼럼 이름 변경")
    @PatchMapping("/columns/{column_id}/name")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> updateColumnName(
            @RequestBody ColumnsRequestDto.UpdateColumnNameRequestDto requestDto,
            @PathVariable Long column_id) {
        ColumnsResponseDto columnsResponseDto = columnsService.updateColumnName(requestDto, column_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "칼럼 이름 수정 성공", columnsResponseDto));
    }


    @Operation(summary = "칼럼 색상 변경")
    @PatchMapping("/columns/{column_id}/color")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> updateColumnColor(
            @RequestBody ColumnsRequestDto.UpdateColumnColorRequestDto requestDto,
            @PathVariable Long column_id) {
        ColumnsResponseDto columnsResponseDto = columnsService.updateColumnColor(requestDto, column_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value()
                        , "칼럼 색상 수정 성공"
                        , columnsResponseDto));
    }


    @Operation(summary = "칼럼 순서 변경")
    @PatchMapping("/columns/{column_id}/sequence")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> updateColumnSequence(
            @RequestBody ColumnsRequestDto.UpdateColumnSequenceRequestDto requestDto,
            @PathVariable Long column_id) {
        ColumnsResponseDto columnsResponseDto = columnsService.updateColumnSequence(requestDto, column_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value()
                        , "칼럼 순서 수정 성공"
                        , columnsResponseDto));
    }


    @Operation(summary = "칼럼 삭제")
    @PatchMapping("/columns/{column_id}")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> deleteColumnSequence(
            @PathVariable Long column_id) {
        ColumnsResponseDto columnsResponseDto = columnsService.deleteColumnSequence(column_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value(), "칼럼 삭제 성공", columnsResponseDto));
    }


    @Operation(summary = "칼럼 전체 조회")
    @GetMapping("/boards/{board_id}/columns")
    public ResponseEntity<CommonResponseDto<List<ColumnsResponseDto>>> getColumnList(
            @PathVariable Long board_id) {
        List<ColumnsResponseDto> columnsResponseDtoList = columnsService.getColumnList(board_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value()
                        , "칼럼 조회 성공"
                        , columnsResponseDtoList));
    }


    @Operation(summary = "칼럼 단일 조회")
    @GetMapping("/columns/{column_id}")
    public ResponseEntity<CommonResponseDto<ColumnsResponseDto>> getColumn(
            @PathVariable Long column_id) {
        ColumnsResponseDto columnsResponseDto = columnsService.getColumn(column_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponseDto(HttpStatus.OK.value()
                        , "해당 칼럼 조회 성공"
                        , columnsResponseDto));
    }
}
