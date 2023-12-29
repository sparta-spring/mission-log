package com.sparta.missionreport.domain.checklist.service;

import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.service.CardService;
import com.sparta.missionreport.domain.checklist.dto.ChecklistDto;
import com.sparta.missionreport.domain.checklist.entity.Checklist;
import com.sparta.missionreport.domain.checklist.exception.ChecklistCustomException;
import com.sparta.missionreport.domain.checklist.exception.ChecklistExceptionCode;
import com.sparta.missionreport.domain.checklist.repository.ChecklistRepository;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChecklistService {

    private final CardService cardService;
    private final UserService userService;
    private final ChecklistRepository checklistRepository;

    @Transactional
    public ChecklistDto.Response createChecklist(Long card_id, User user,
            ChecklistDto.CreateRequest request) {
        User createdBy = userService.findUserById(user.getId());
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        Long sequence = checklistRepository.countByCardIdAndIsDeletedFalse(card_id);
        Checklist checklist = checklistRepository.save(request.toEntity(sequence + 1, card));

        return ChecklistDto.Response.of(checklist);
    }

    @Transactional
    public ChecklistDto.Response updateContent(
            Long card_id, Long checklist_id, User user, ChecklistDto.UpdateRequest request) {
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        Checklist checklist = getChecklistAndCheckAuth(user, checklist_id);

        checklist.update(request);
        return ChecklistDto.Response.of(checklist);
    }

    @Transactional
    public ChecklistDto.Response updateCheck(
            Long card_id, Long checklist_id, User user) {
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        Checklist checklist = getChecklistAndCheckAuth(user, checklist_id);

        checklist.updateIsChecked();
        return ChecklistDto.Response.of(checklist);
    }

    @Transactional
    public ChecklistDto.Response updateSequence(
            Long card_id, Long checklist_id, User user,
            ChecklistDto.UpdateSequenceRequest request) {
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        Checklist checklist = getChecklistAndCheckAuth(user, checklist_id);

        if (checklist.getSequence() < request.getSequence()) {
            List<Checklist> updateChecklistList =
                    checklistRepository.findAllByCardIdAndIsDeletedFalseAndSequenceBetween(
                            card_id, checklist.getSequence(), request.getSequence());
        } else if (request.getSequence() < checklist.getSequence()) {
            return ChecklistDto.Response.of(checklist);
        } else {
            throw new ColumnsCustomException(ColumnsExceptionCode.NOT_CHANGE_COLUMN_SEQUENCE);
        }

        if (column.getSequence() < requestDto.getSequence()) {
            List<Columns> columnsList = columnsRepository.findAllBySequenceAndIsDeleteFalseBetween(
                    column.getSequence() + 1, requestDto.getSequence());
            for (Columns columns : columnsList) {
                columns.updateSequence("-", 1L);
            }
            column.updateSequence("+", requestDto.getSequence() - column.getSequence());
        } else if (column.getSequence() > requestDto.getSequence()) {
            List<Columns> columnsList = columnsRepository.findAllBySequenceAndIsDeleteFalseBetween(
                    requestDto.getSequence(), column.getSequence() - 1);
            for (Columns columns : columnsList) {
                columns.updateSequence("+", 1L);
            }
            column.updateSequence("-", column.getSequence() - requestDto.getSequence());

        } else {
            throw new ColumnsCustomException(ColumnsExceptionCode.NOT_CHANGE_COLUMN_SEQUENCE);
        }
    }

    @Transactional
    public void deleteChecklist(Long card_id, Long checklist_id, User user) {

    }

    public ChecklistDto.Response getChecklist(Long card_id, Long checklist_id, User user) {

    }

    public Checklist getChecklistAndCheckAuth(User user, Long checklistId) {
        Checklist checklist = findChecklistById(checklistId);
        User loginUser = userService.findUserById(user.getId());
        Card card = cardService.getCardAndCheckAuth(loginUser, checklist.getCard().getId());

        return checklist;
    }

    public Checklist findChecklistById(Long checklistId) {
        return checklistRepository.findById(checklistId).orElseThrow(
                () -> new ChecklistCustomException(ChecklistExceptionCode.NOT_FOUND_CHECKLIST));
    }

}
