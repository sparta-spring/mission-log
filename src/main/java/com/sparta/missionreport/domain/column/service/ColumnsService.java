package com.sparta.missionreport.domain.column.service;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.service.BoardService;
import com.sparta.missionreport.domain.column.dto.ColumnsRequestDto;
import com.sparta.missionreport.domain.column.dto.ColumnsResponseDto;
import com.sparta.missionreport.domain.column.entity.Columns;
import com.sparta.missionreport.domain.column.exception.ColumnsCustomException;
import com.sparta.missionreport.domain.column.exception.ColumnsExceptionCode;
import com.sparta.missionreport.domain.column.repository.ColumnsRepository;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColumnsService {

    private final ColumnsRepository columnsRepository;
    private final UserService userService;
    private final BoardService boardService;

    public ColumnsResponseDto addColumn(ColumnsRequestDto.AddColumnRequestDto requestDto, Long userId, Long boardId) {
        Board board = boardService.findBoard(boardId);
        User user = userService.findUserById(userId);
        validateDuplicateName(requestDto.getName());
        Long sequence = columnsRepository.findTopByBoardIdOrderBySequenceDesc(boardId).orElseThrow(
               () -> new ColumnsCustomException(ColumnsExceptionCode.NOT_FOUND_COLUMNS)).getSequence() + 1;
        Columns column = Columns.builder()
                .name(requestDto.getName())
                .board(board)
                .sequence(sequence)
                .build();
        columnsRepository.save(column);
        return new ColumnsResponseDto(column);
    }

    @Transactional
    public ColumnsResponseDto updateColumName(ColumnsRequestDto.UpdateColumnNameRequestDto requestDto,
                                              Long userId,
                                              Long columnId)
    {
        Columns column = findColumns(columnId);
        User user = userService.findUserById(userId);
        validateDuplicateName(requestDto.getName());
        column.updateName(requestDto.getName());
        return new ColumnsResponseDto(column);
    }

    public Columns findColumns(Long columnsId) {
        return columnsRepository.findById(columnsId).orElseThrow(
                () -> new ColumnsCustomException(ColumnsExceptionCode.NOT_FOUND_COLUMNS)
        );
    }

    private void validateDuplicateName(String name){
        columnsRepository.findByName(name).ifPresent(m -> {
            throw new ColumnsCustomException(ColumnsExceptionCode.DUPLICATE_COLUM_NAME);
        });
    }
}
