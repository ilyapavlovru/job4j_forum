package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.repository.CommentMemRepository;

//@Service
public class CommentService {

    private CommentMemRepository commentMemRepository;

    public CommentService(CommentMemRepository commentMemRepository) {
        this.commentMemRepository = commentMemRepository;
    }

    public Comment saveComment(Comment comment) {
        return commentMemRepository.saveComment(comment);
    }
}
