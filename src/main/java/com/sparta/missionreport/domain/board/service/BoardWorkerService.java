package com.sparta.missionreport.domain.board.service;

import com.sparta.missionreport.domain.board.entity.Board;
import com.sparta.missionreport.domain.board.entity.BoardWorker;
import com.sparta.missionreport.domain.board.repository.BoardWorkerRepository;
import com.sparta.missionreport.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardWorkerService {

    private final BoardWorkerRepository boardWorkerRepository;

    public boolean isExistedWorker(User user, Board board) {
        return boardWorkerRepository.existsByUser_IdAndBoard_Id(user.getId(), board.getId());
    }

    @Transactional
    public void saveBoardWorker(Board saveBoard, User newWorker) {
        BoardWorker boardWorker = BoardWorker.builder().user(newWorker).board(saveBoard).build();
        boardWorkerRepository.save(boardWorker);
    }
}