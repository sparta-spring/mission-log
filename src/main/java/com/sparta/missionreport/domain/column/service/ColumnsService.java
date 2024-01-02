package com.sparta.missionreport.domain.column.service;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.exception.BoardCustomException;
import com.sparta.missionreport.domain.board.exception.BoardExceptionCode;
import com.sparta.missionreport.domain.board.service.BoardService;
import com.sparta.missionreport.domain.board.service.BoardWorkerService;
import com.sparta.missionreport.domain.column.dto.ColumnsRequestDto;
import com.sparta.missionreport.domain.column.dto.ColumnsResponseDto;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.column.exception.ColumnsCustomException;
import com.sparta.missionreport.domain.column.exception.ColumnsExceptionCode;
import com.sparta.missionreport.domain.column.repository.ColumnsRepository;
import com.sparta.missionreport.domain.user.entity.User;
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
    private final BoardWorkerService boardWorkerService;

    public ColumnsResponseDto addColumn(ColumnsRequestDto.AddColumnRequestDto requestDto, Long boardId, User user) {
        Board board = boardService.findBoardByID(boardId);
        if(!boardWorkerService.isExistedWorker(user, board)) {
            throw new BoardCustomException(BoardExceptionCode.NOT_AUTHORIZATION_ABOUT_BOARD);
        }
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
                                               Long columnId, User user)
    {
        Columns column = findColumns(columnId);
        if(!boardWorkerService.isExistedWorker(user, column.getBoard())) {
            throw new BoardCustomException(BoardExceptionCode.NOT_AUTHORIZATION_ABOUT_BOARD);
        }
        validateDuplicateName(requestDto.getName(), column.getBoard().getId());
        column.updateName(requestDto.getName());
        return new ColumnsResponseDto(column);
    }

    @Transactional
    public ColumnsResponseDto updateColumnColor(ColumnsRequestDto.UpdateColumnColorRequestDto requestDto, Long columnId, User user) {
        Columns column = findColumns(columnId);
        if(!boardWorkerService.isExistedWorker(user, column.getBoard())) {
            throw new BoardCustomException(BoardExceptionCode.NOT_AUTHORIZATION_ABOUT_BOARD);
        }
        column.updateColor(requestDto.getColor());
        return new ColumnsResponseDto(column);
    }


    @Transactional
    public ColumnsResponseDto updateColumnSequence(ColumnsRequestDto.UpdateColumnSequenceRequestDto requestDto,
                                                   Long columnId, User user)
    {
        Columns column = findColumns(columnId);
        if(!boardWorkerService.isExistedWorker(user, column.getBoard())) {
            throw new BoardCustomException(BoardExceptionCode.NOT_AUTHORIZATION_ABOUT_BOARD);
        }
        Long boardId = column.getBoard().getId();
        if(column.getSequence() < requestDto.getSequence()){
            List<Columns> columnsList = columnsRepository.findAllByIsDeletedFalseAndBoardIdAndSequenceBetween(boardId, column.getSequence() + 1L, requestDto.getSequence());
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
            throw new ColumnsCustomException(ColumnsExceptionCode.BAD_REQUEST_NOT_CHANGE_COLUMN_SEQUENCE);
        }
        return new ColumnsResponseDto(column);
    }

    @Transactional
    public ColumnsResponseDto deleteColumnSequence(Long columnId, User user) {
        Columns column = findColumns(columnId);
        if(!boardWorkerService.isExistedWorker(user, column.getBoard())) {
            throw new BoardCustomException(BoardExceptionCode.NOT_AUTHORIZATION_ABOUT_BOARD);
        }
        Long boardId = column.getBoard().getId();
        if(!column.getCardList().isEmpty()){
            throw new ColumnsCustomException(ColumnsExceptionCode.NOT_ALLOWED_DELETE_COLUM);
        }
        List<Columns> columnsList = columnsRepository.findAllByBoardIdAndIsDeletedFalseAndSequenceGreaterThan(boardId, column.getSequence());
        for(Columns columns : columnsList){
            columns.updateSequence("-", 1L);
        }
        column.updateDelete();
        return null;
    }


    public List<ColumnsResponseDto> getColumnList(Long boardId, User user) {
        Board board = boardService.findBoardByID(boardId);
        if(!boardWorkerService.isExistedWorker(user, board)) {
            throw new BoardCustomException(BoardExceptionCode.NOT_AUTHORIZATION_ABOUT_BOARD);
        }
        return columnsRepository.findAllByBoardIdAndIsDeletedFalseOrderBySequence(boardId).stream().map(ColumnsResponseDto::new).toList();
    }

    public ColumnsResponseDto getColumn(Long columnId, User user) {
        Columns columns = findColumns(columnId);
        if(!boardWorkerService.isExistedWorker(user, columns.getBoard())) {
            throw new BoardCustomException(BoardExceptionCode.NOT_AUTHORIZATION_ABOUT_BOARD);
        }
        return new ColumnsResponseDto(columns);
    }

    public Columns findColumns(Long columnsId) {
        return columnsRepository.findByIdAndIsDeletedFalse(columnsId).orElseThrow(
                () -> new ColumnsCustomException(ColumnsExceptionCode.NOT_FOUND_COLUMNS)
        );
    }

    private void validateDuplicateName(String name, Long boardId){
        columnsRepository.findByNameAndIsDeletedFalseAndBoardId(name, boardId).ifPresent(m -> {
            throw new ColumnsCustomException(ColumnsExceptionCode.CONFLICT_DUPLICATE_COLUMN_NAME);
        });
    }
}
