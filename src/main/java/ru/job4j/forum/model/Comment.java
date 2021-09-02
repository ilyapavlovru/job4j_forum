package ru.job4j.forum.model;

import java.util.Calendar;
import java.util.Objects;

public class Comment {
    private int id;
    private String text;
    private Calendar created;

    public static Comment of(int id, String text) {
        Comment comment = new Comment();
        comment.id = id;
        comment.text = text;
        return comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return id == comment.id
                && Objects.equals(text, comment.text)
                && Objects.equals(created, comment.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, created);
    }
}
