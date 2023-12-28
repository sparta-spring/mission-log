package com.sparta.missionreport.domain.board.service;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.exception.BoardCustomException;
import com.sparta.missionreport.domain.board.exception.BoardExceptionCode;
import com.sparta.missionreport.domain.board.repository.BoardRepository;
import com.sparta.missionreport.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    public Board findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_BOARD));
    }

    public void createBoard(User user) {
    }
}

