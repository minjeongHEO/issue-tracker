package com.issuetracker.comment.service;

import com.issuetracker.comment.domain.Comment;
import com.issuetracker.comment.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> findCommentsById(Long id) {
        return commentRepository.findAllByIssueId(id);
    }
}
