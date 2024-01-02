package com.sparta.missionreport.domain.board.service;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.entity.BoardWorker;
import com.sparta.missionreport.domain.board.exception.BoardCustomException;
import com.sparta.missionreport.domain.board.exception.BoardExceptionCode;
import com.sparta.missionreport.domain.board.repository.BoardWorkerRepository;
import com.sparta.missionreport.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardWorkerService {

    private final BoardWorkerRepository boardWorkerRepository;

    public boolean isExistedWorker(User user, Board board) {
        return boardWorkerRepository.existsByUser_IdAndBoard_IdAndIsDeletedIsFalse(user.getId(), board.getId());
    }

    @Transactional
    public void saveBoardWorker(Board saveBoard, User newWorker) {
        BoardWorker boardWorker = BoardWorker.builder().user(newWorker).board(saveBoard).build();
        boardWorkerRepository.save(boardWorker);
    }

    @Transactional
    public void deleteBoardWorkers(Board board) {
        List<BoardWorker> list = boardWorkerRepository.findAllByBoard_IdAndIsDeletedIsFalse(board.getId());
        for (BoardWorker boardWorker : list) {
            boardWorker.delete();
        }
    }

    public List<BoardWorker> findAllByWorkersInBoard(Board board) {
        return boardWorkerRepository.findAllByBoard_IdAndIsDeletedIsFalse(board.getId());
    }

    public BoardWorker serchBoardUser(Board board, User searchBoardUser) {
        return boardWorkerRepository.findByBoard_IdAndIsDeletedIsFalseAndUser_Email(board.getId(), searchBoardUser.getEmail()).orElseThrow(
                () -> new BoardCustomException(BoardExceptionCode.NOT_FOUND_WORKER_IN_BOARD));
    }

    @Transactional
    public void deleteBoardWorker(Board board, User boardWorker) {
        BoardWorker worker = boardWorkerRepository.findBoardWorkerByBoard_IdAndUser_Id(board.getId(), boardWorker.getId()).orElseThrow(
                ()-> new BoardCustomException(BoardExceptionCode.NOT_FOUND_WORKER_IN_BOARD)
        );
    }
}