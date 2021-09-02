package ru.job4j.forum.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;

@Controller
public class IndexControl {

    private final PostService postService;

    public IndexControl(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        Collection<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", postService.findAllPosts());
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        return "post/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Post post, HttpServletRequest req) {
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        Optional<Post> accident = postService.findPostById(id);
        accident.ifPresent(value -> model.addAttribute("post", value));
        return "post/update";
    }
}
