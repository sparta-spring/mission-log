package com.sparta.missionreport.domain.checklist.repository;

import com.sparta.missionreport.domain.checklist.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long>,
        ChecklistRepositoryCustom {

    Long countByCardIdAndIsDeletedFalse(Long cardId);

}
