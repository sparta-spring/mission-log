package com.sparta.missionreport.domain.comment.repository;

import com.sparta.missionreport.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndIsDeletedIsFalse(Long comment_id);
    List<Comment> findAllByCard_IdAndIsDeletedIsFalse(Long card_id);
}
