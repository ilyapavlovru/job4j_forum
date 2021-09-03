package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostMemRepository {

    private final Map<Integer, Post> posts = new HashMap<>();
    private final static AtomicInteger ACCIDENT_ID = new AtomicInteger(2);
    private final static AtomicInteger COMMENT_ID = new AtomicInteger(2);

    private PostMemRepository() {
        Post post1 = Post.of(1, "Продаю машину ладу 01");
        Post post2 = Post.of(2, "Продаю ноутбук HP Pavilion Gaming 15-ec1089ur");
        posts.put(1, post1);
        posts.put(2, post2);

        post1.addComment(Comment.of(1, "Отличный пост!"));
        post1.addComment(Comment.of(2, "Какова цена?"));
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Post savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(ACCIDENT_ID.incrementAndGet());
            posts.put(post.getId(), post);
            return post;
        }
        Post foundPost = findPostById(post.getId()).get();
        foundPost.setName(post.getName());
        posts.put(post.getId(), foundPost);
        return foundPost;
    }

    public Optional<Post> findPostById(int id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Comment saveComment(Comment comment) {
        comment.setId(COMMENT_ID.incrementAndGet());
        return comment;
    }
}
