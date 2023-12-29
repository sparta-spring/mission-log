package com.sparta.missionreport.domain.checklist.repository;

import com.sparta.missionreport.domain.checklist.entity.Checklist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    Long countByCardIdAndIsDeletedFalse(Long cardId);

    List<Checklist> findAllByCardIdAndIsDeletedFalseAndSequenceBetween(
            Long cardId, Long oldSequence, Long newSequence);
}
