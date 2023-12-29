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

        // ToDo: sequence 변경

        return ChecklistDto.Response.of(checklist);
    }

    @Transactional
    public void deleteChecklist(Long card_id, Long checklist_id, User user) {
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        Checklist checklist = getChecklistAndCheckAuth(user, checklist_id);

        checklist.deleteChecklist();

        // ToDo: sequence 땡기기
    }

    public ChecklistDto.Response getChecklist(Long card_id, Long checklist_id, User user) {
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        Checklist checklist = getChecklistAndCheckAuth(user, checklist_id);

        return ChecklistDto.Response.of(checklist);
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
