package com.sparta.missionreport.domain.column.service;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.service.BoardService;
import com.sparta.missionreport.domain.column.dto.ColumnsRequestDto;
import com.sparta.missionreport.domain.column.dto.ColumnsResponseDto;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.column.exception.ColumnsCustomException;
import com.sparta.missionreport.domain.column.exception.ColumnsExceptionCode;
import com.sparta.missionreport.domain.column.repository.ColumnsRepository;
import com.sparta.missionreport.global.enums.Color;
import com.sparta.missionreport.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ColumnsService {

    private final ColumnsRepository columnsRepository;
    private final BoardService boardService;

    public ColumnsResponseDto addColumn(ColumnsRequestDto.AddColumnRequestDto requestDto, Long boardId) {
        Board board = boardService.findBoard(boardId);
        validateDuplicateName(requestDto.getName());
        Columns check = columnsRepository.findTopByBoardIdOrderBySequenceDesc(boardId).orElse(null);
        Long sequence;
        if(check == null){
            sequence = 1L;
        }
        else {
            sequence = check.getSequence() + 1;
        }
        Columns column = Columns.builder()
                .name(requestDto.getName())
                .board(board)
                .color(Color.NONE)
                .sequence(sequence)
                .build();
        columnsRepository.save(column);
        return new ColumnsResponseDto(column);
    }

    @Transactional
    public ColumnsResponseDto updateColumnName(ColumnsRequestDto.UpdateColumnNameRequestDto requestDto,
                                              Long columnId)
    {
        Columns column = findColumns(columnId);
        validateDuplicateName(requestDto.getName());
        column.updateName(requestDto.getName());
        return new ColumnsResponseDto(column);
    }

    @Transactional
    public ColumnsResponseDto updateColumnColor(ColumnsRequestDto.UpdateColumnColorRequestDto requestDto, Long columnId) {
        Columns column = findColumns(columnId);
        column.updateColor(requestDto.getColor());
        return new ColumnsResponseDto(column);
    }


    @Transactional
    public ColumnsResponseDto updateColumnSequence(ColumnsRequestDto.UpdateColumnSequenceRequestDto requestDto,
                                                   Long columnId)
    {
        Columns column = findColumns(columnId);
        if(column.getSequence() < requestDto.getSequence()){
            List<Columns> columnsList = columnsRepository.findAllBySequenceBetween(column.getSequence() + 1, requestDto.getSequence());
            for(Columns columns : columnsList){
                columns.updateSequence("-", 1L);
            }
            column.updateSequence("+", requestDto.getSequence() - column.getSequence());
        }
        else if (column.getSequence() > requestDto.getSequence()){
            List<Columns> columnsList = columnsRepository.findAllBySequenceBetween(requestDto.getSequence(), column.getSequence() - 1);
            for(Columns columns : columnsList){
                columns.updateSequence("+", 1L);
            }
            column.updateSequence("-",  column.getSequence() - requestDto.getSequence());

        }
        else {
            throw new ColumnsCustomException(ColumnsExceptionCode.NOT_CHANGE_COLUMN_SEQUENCE);
        }
        return new ColumnsResponseDto(column);
    }

    public Columns findColumns(Long columnsId) {
        return columnsRepository.findById(columnsId).orElseThrow(
                () -> new ColumnsCustomException(ColumnsExceptionCode.NOT_FOUND_COLUMNS)
        );
    }

    private void validateDuplicateName(String name){
        columnsRepository.findByName(name).ifPresent(m -> {
            throw new ColumnsCustomException(ColumnsExceptionCode.DUPLICATE_COLUMN_NAME);
        });
    }
}
