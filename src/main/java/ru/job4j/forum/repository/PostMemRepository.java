package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
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

    private PostMemRepository() {
        Post post1 = Post.of(1, "Продаю машину ладу 01");
        Post post2 = Post.of(2, "Продаю ноутбук HP Pavilion Gaming 15-ec1089ur");
        posts.put(1, post1);
        posts.put(2, post2);
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public void savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(ACCIDENT_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public Optional<Post> findPostById(int id) {
        return Optional.ofNullable(posts.get(id));
    }
}
