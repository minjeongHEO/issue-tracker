package com.issuetracker.comment.repository;

import com.issuetracker.comment.domain.Comment;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllByIssueId(Long issueId);
}
