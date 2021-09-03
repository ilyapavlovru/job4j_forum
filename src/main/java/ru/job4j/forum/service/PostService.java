package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.PostMemRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class PostService {

    private PostMemRepository postMemRepository;

    public PostService(PostMemRepository postMemRepository) {
        this.postMemRepository = postMemRepository;
    }

    public Collection<Post> findAllPosts() {
        return postMemRepository.findAllPosts();
    }

    public void savePost(Post post) {
        postMemRepository.savePost(post);
    }

    public Optional<Post> findPostById(int id) {
        return postMemRepository.findPostById(id);
    }

    public Comment saveComment(Comment comment) {
        return postMemRepository.saveComment(comment);
    }
}
