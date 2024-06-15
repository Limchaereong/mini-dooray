package com.example.demo.repository;

import com.example.demo.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTaskTaskId(Long taskId);

    @Modifying
    @Transactional
    @Query("update Comment c set c.commentContent = :commentContent where c.commentId = :commentId")
    void updateByCommentId(@Param("commentId") Long commentId, @Param("commentContent") String commentContent);
}
