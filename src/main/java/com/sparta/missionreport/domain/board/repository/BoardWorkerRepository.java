package com.sparta.missionreport.domain.board.repository;

import com.sparta.missionreport.domain.board.entity.BoardWorker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardWorkerRepository extends JpaRepository<BoardWorker, Long> {
    Boolean existsByUser_IdAndBoard_Id(Long userId, Long boardId);
}
