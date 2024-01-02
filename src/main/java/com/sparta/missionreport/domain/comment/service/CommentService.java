package com.sparta.missionreport.domain.comment.service;

import com.sparta.missionreport.domain.board.service.BoardWorkerService;
import com.sparta.missionreport.domain.card.entity.Card;
import com.sparta.missionreport.domain.card.service.CardService;
import com.sparta.missionreport.domain.comment.dto.CommentDto;
import com.sparta.missionreport.domain.comment.entity.Comment;
import com.sparta.missionreport.domain.comment.exception.CommentCustomException;
import com.sparta.missionreport.domain.comment.exception.CommentExceptionCode;
import com.sparta.missionreport.domain.comment.repository.CommentRepository;
import com.sparta.missionreport.domain.user.entity.User;
import com.sparta.missionreport.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final UserService userService;
    private final BoardWorkerService boardWorkerService;
    private final CardService cardService;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentDto.CommentResponse createComment(User user, Long cardId, CommentDto.CreateCommentRequest request) {
        Card card = cardService.findCardById(cardId);
        User requestUser = userService.findUserById(user.getId());

        if (!boardWorkerService.isExistedWorker(requestUser, card.getColumns().getBoard())) {
            throw new CommentCustomException(CommentExceptionCode.NOT_AUTHORIZATION_ABOUT_REQUEST);
        }

        Comment savedComment = commentRepository.save(request.toEntity(requestUser, card));
        return CommentDto.CommentResponse.of(savedComment);
    }

    @Transactional
    public CommentDto.CommentResponse updateComment(User user, Long commentId, CommentDto.UpdateCommentRequest request) {
        Comment comment = getCommentAndCheckUser(user, commentId);

        comment.update(request);

        return CommentDto.CommentResponse.of(comment);
    }

    @Transactional
    public void deleteComment(User user, Long commentId) {
        Comment comment = getCommentAndCheckUser(user, commentId);
        comment.delete();
    }

    public List<CommentDto.CommentResponse> getCommentsInCard(User user, Long cardId) {
        Card card = cardService.getCardAndCheckAuth(user, cardId);
        List<Comment> list = commentRepository.findAllByCard_IdAndIsDeletedIsFalse(card.getId());

        return list.stream().map(CommentDto.CommentResponse::of).toList();
    }

    public CommentDto.CommentResponse getComment(User user, Long commentId) {
        Comment comment = getCommentAndCheckUser(user, commentId);
        return CommentDto.CommentResponse.of(comment);
    }

    private Comment getCommentAndCheckUser(User user, Long commentId) {
        Comment comment = findCommentById(commentId);

        if (!comment.getCreatedBy().getId().equals(user.getId())) {
            throw new CommentCustomException(CommentExceptionCode.NOT_AUTHORIZATION_ABOUT_COMMENT);
        }
        return comment;
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findByIdAndIsDeletedIsFalse(commentId).orElseThrow(
                () -> new CommentCustomException(CommentExceptionCode.NOT_FOUND_COMMENT)
        );
    }
}
