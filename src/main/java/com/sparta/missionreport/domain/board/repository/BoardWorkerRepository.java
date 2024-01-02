package com.sparta.missionreport.domain.board.repository;

import com.sparta.missionreport.domain.board.entity.BoardWorker;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardWorkerRepository extends JpaRepository<BoardWorker, Long> {
    Boolean existsByUser_IdAndBoard_IdAndIsDeletedIsFalse(Long userId, Long boardId);

    List<BoardWorker> findAllByBoard_IdAndIsDeletedIsFalse(Long boardId);

    Optional<BoardWorker> findByBoard_IdAndIsDeletedIsFalseAndUser_Email(Long userId, String email);

    Optional<BoardWorker> findBoardWorkerByBoard_IdAndUser_Id(Long board_id, Long user_id);
}
