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
import com.sparta.missionreport.global.common.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColumnsService {

    private final ColumnsRepository columnsRepository;
    private final UserService userService;
    private final BoardService boardService;

    public ColumnsResponseDto addColumn(ColumnsRequestDto.AddColumnsRequestDto requestDto, Long userId, Long boardId) {
        Board board = boardService.findBoard(boardId);
        User user = userService.findUser(userId);
        validateDuplicateName(requestDto.getName());
        Long sequence = columnsRepository.count() + 1;
        Columns columns = Columns.builder()
                .name(requestDto.getName())
                .sequence(sequence)
                .build();
        return new ColumnsResponseDto(columns);
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
