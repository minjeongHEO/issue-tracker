package com.issuetracker.comment.repository;

import com.issuetracker.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByIssueId(Long issueId);

    @Modifying
    @Query("UPDATE comment SET content = :content, file_id = :fileId WHERE id = :id")
    int updateBodyById(Long id, String content, Long fileId);
}
