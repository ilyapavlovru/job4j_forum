package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Comment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//@Repository
public class CommentMemRepository {
    private final Map<Integer, Comment> comments = new HashMap<>();
    private final static AtomicInteger COMMENT_ID = new AtomicInteger(2);

    public Comment saveComment(Comment comment) {
        comment.setId(COMMENT_ID.incrementAndGet());
        comment.setCreated(Calendar.getInstance());
        comments.put(comment.getId(), comment);
        return comment;
    }
}
