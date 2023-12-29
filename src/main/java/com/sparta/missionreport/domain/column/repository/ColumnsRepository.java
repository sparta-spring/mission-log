package com.sparta.missionreport.domain.column.repository;

import com.sparta.missionreport.domain.column.dto.ColumnsResponseDto;
import com.sparta.missionreport.domain.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColumnsRepository extends JpaRepository<Columns, Long> {

    Optional<Columns> findTopByBoardIdAndIsDeletedFalseOrderBySequenceDesc(Long boardId);

    List<Columns> findAllByIsDeletedFalseAndBoardIdAndSequenceBetween(Long boardId, long l, Long sequence);

    List<Columns> findAllByBoardIdAndIsDeletedFalseAndSequenceGreaterThan(Long boardId, Long sequence);

    Optional<Columns> findByIdAndIsDeletedFalse(Long columnsId);

    Optional<Columns> findByNameAndIsDeletedFalseAndBoardId(String name, Long boardId);

    List<Columns> findAllByBoardIdAndIsDeletedFalseOrderBySequence(Long boardId);
}
