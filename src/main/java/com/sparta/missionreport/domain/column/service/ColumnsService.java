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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnsService {

    private final ColumnsRepository columnsRepository;
    private final BoardService boardService;

    public ColumnsResponseDto addColumn(ColumnsRequestDto.AddColumnRequestDto requestDto, Long boardId) {
        Board board = boardService.findBoard(boardId);
        validateDuplicateName(requestDto.getName(), boardId);
        Columns check = columnsRepository.findTopByBoardIdAndIsDeletedFalseOrderBySequenceDesc(boardId).orElse(null);
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
                .isDeleted(false)
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
        validateDuplicateName(requestDto.getName(), column.getBoard().getId());
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
        Long boardId = column.getBoard().getId();
        if(column.getSequence() < requestDto.getSequence()){
            List<Columns> columnsList = columnsRepository.findAllByIsDeletedFalseAndBoardIdAndSequenceBetween(boardId, column.getSequence() + 1, requestDto.getSequence());
            for(Columns columns : columnsList){
                columns.updateSequence("-", 1L);
            }
            column.updateSequence("+", requestDto.getSequence() - column.getSequence());
        }
        else if (column.getSequence() > requestDto.getSequence()){
            List<Columns> columnsList = columnsRepository.findAllByIsDeletedFalseAndBoardIdAndSequenceBetween(boardId, requestDto.getSequence(), column.getSequence() - 1);
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

    @Transactional
    public ColumnsResponseDto deleteColumnSequence(Long columnId) {
        Columns column = findColumns(columnId);
        Long boardId = column.getBoard().getId();
        if(!column.getCardList().isEmpty()){
            throw new ColumnsCustomException(ColumnsExceptionCode.NOT_ALLOW_DELETE_COLUM);
        }
        List<Columns> columnsList = columnsRepository.findAllByBoardIdAndIsDeletedFalseAndSequenceGreaterThan(boardId, column.getSequence());
        for(Columns columns : columnsList){
            columns.updateSequence("-", 1L);
        }
        column.updateDelete();
        return null;
    }


    public List<ColumnsResponseDto> getColumnList(Long boardId) {
        return columnsRepository.findAllByBoardIdAndIsDeletedFalseOrderBySequence(boardId).stream().map(ColumnsResponseDto::new).toList();
    }

    public ColumnsResponseDto getColumn(Long columnId) {
        return new ColumnsResponseDto(findColumns(columnId));
    }
    public Columns findColumns(Long columnsId) {
        return columnsRepository.findByIdAndIsDeletedFalse(columnsId).orElseThrow(
                () -> new ColumnsCustomException(ColumnsExceptionCode.NOT_FOUND_COLUMNS)
        );
    }

    private void validateDuplicateName(String name, Long boardId){
        columnsRepository.findByNameAndIsDeletedFalseAndBoardId(name, boardId).ifPresent(m -> {
            throw new ColumnsCustomException(ColumnsExceptionCode.DUPLICATE_COLUMN_NAME);
        });
    }
}
