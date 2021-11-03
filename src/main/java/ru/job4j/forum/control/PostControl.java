package ru.job4j.forum.control;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.CommentService;
import ru.job4j.forum.service.PostService;
import ru.job4j.forum.service.UserService;

import java.util.Optional;

@Controller
public class PostControl {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    public PostControl(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "post/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Post post) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(userName);
        post.setUser(user);
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        UserDetails userDetails = (UserDetails) model.getAttribute("user");
        String userName = userDetails.getUsername();
        User user = userService.findByUserName(userName);

        Optional<Post> post = postService.findPostById(id);

        String errorMessage;
        if (post.isEmpty()) {
            errorMessage = "Пост не найден для заданного идентификатора";
            model.addAttribute("errorMessage", errorMessage);
            return "failure";
        }

        if (user.getId() == post.get().getUser().getId()) {
            post.ifPresent(value -> model.addAttribute("post", value));
            return "post/update";
        } else {
            errorMessage = "Вы не можете редактировать тему чужого поста!";
            model.addAttribute("errorMessage", errorMessage);
            return "failure";
        }
    }

    @GetMapping("/show")
    public String show(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Optional<Post> post = postService.findPostById(id);
        post.ifPresent(value -> model.addAttribute("post", value));
        return "post";
    }

    @GetMapping("/addComment")
    public String addComment(@RequestParam("id") int id, Model model) {
        Optional<Post> post = postService.findPostById(id);
        post.ifPresent(value -> model.addAttribute("post", value));
        return "comment/create";
    }

    @PostMapping("/saveComment")
    public String saveComment(@RequestParam("id") int id, @ModelAttribute Comment comment, Model model) {
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(userName);
        Post post = postService.findPostById(id).get();
        Comment createdComment = new Comment();
        createdComment.setText(comment.getText());
        createdComment.setUser(user);
        createdComment.setPost(post);
        commentService.saveComment(createdComment);
        model.addAttribute("post", post);
        return "post";
    }
}
