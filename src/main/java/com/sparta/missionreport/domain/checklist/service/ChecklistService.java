package com.sparta.missionreport.domain.checklist.service;

import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.exception.CardCustomException;
import com.sparta.missionreport.domain.card.exception.CardExceptionCode;
import com.sparta.missionreport.domain.card.service.CardService;
import com.sparta.missionreport.domain.checklist.dto.ChecklistDto;
import com.sparta.missionreport.domain.checklist.entity.Checklist;
import com.sparta.missionreport.domain.checklist.exception.ChecklistCustomException;
import com.sparta.missionreport.domain.checklist.exception.ChecklistExceptionCode;
import com.sparta.missionreport.domain.checklist.repository.ChecklistRepository;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public ChecklistDto.ChecklistResponse createChecklist(Long card_id, User user,
            ChecklistDto.CreateChecklistRequest request) {
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        Long sequence = checklistRepository.countByCardIdAndIsDeletedFalse(card_id);
        Checklist checklist = checklistRepository.save(request.toEntity(sequence + 1, card));

        return ChecklistDto.ChecklistResponse.of(checklist);
    }

    @Transactional
    public ChecklistDto.ChecklistResponse updateContent(
            Long card_id, Long checklist_id, User user,
            ChecklistDto.UpdateChecklistRequest request) {
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        Checklist checklist = getChecklistAndCheckAuth(user, checklist_id);

        checklist.update(request);
        return ChecklistDto.ChecklistResponse.of(checklist);
    }

    @Transactional
    public ChecklistDto.ChecklistResponse updateCheck(
            Long card_id, Long checklist_id, User user) {
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        Checklist checklist = getChecklistAndCheckAuth(user, checklist_id);

        checklist.updateIsChecked();
        return ChecklistDto.ChecklistResponse.of(checklist);
    }

    @Transactional
    public ChecklistDto.ChecklistResponse updateSequence(
            Long card_id, Long checklist_id, User user,
            ChecklistDto.UpdateChecklistSequenceRequest request) {
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        Checklist checklist = getChecklistAndCheckAuth(user, checklist_id);

        if (request.getSequence() < 1 ||
                checklistRepository.countByCardIdAndIsDeletedFalse(card_id) < request.getSequence()
                || Objects.equals(checklist.getSequence(), request.getSequence())) {
            throw new CardCustomException(CardExceptionCode.INVALID_UPDATE_SEQUENCE);
        }

        Long oldSequence = checklist.getSequence();
        Long newSequence = request.getSequence();
        if (newSequence > oldSequence) {
            checklistRepository.decreaseSequence(card.getId(), oldSequence, newSequence);
        } else {
            checklistRepository.increaseSequence(card.getId(), newSequence, oldSequence);
        }
        checklist.updateSequence(newSequence);

        return ChecklistDto.ChecklistResponse.of(checklist);
    }

    @Transactional
    public void deleteChecklist(Long card_id, Long checklist_id, User user) {
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        Checklist checklist = getChecklistAndCheckAuth(user, checklist_id);

        Long sequence = checklist.getSequence();
        Long last = checklistRepository.countByCardIdAndIsDeletedFalse(card_id);
        checklistRepository.decreaseSequence(checklist.getCard().getId(), sequence, last);
        checklist.deleteChecklist();
    }

    public List<ChecklistDto.ChecklistResponse> getChecklists(Long card_id, User user) {
        Card card = cardService.getCardAndCheckAuth(user, card_id);
        List<Checklist> checklists = checklistRepository.findByCardIdAndIsDeletedFalseOrderBySequence(
                card_id);
        List<ChecklistDto.ChecklistResponse> responseList = new ArrayList<>();

        for (Checklist checklist : checklists) {
            responseList.add(ChecklistDto.ChecklistResponse.of(checklist));
        }

        return responseList;
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
