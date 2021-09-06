package ru.job4j.forum.control;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.CommentService;
import ru.job4j.forum.service.PostService;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class PostControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private CommentService commentService;

    @Test
    @WithMockUser
    void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post/create"));
    }

    @Test
    @WithMockUser
    void whenUpdatePostThenOk() throws Exception {
        Post post = Post.of(1, "Продаю машину ладу 01");
        Mockito.when(postService.findPostById(Mockito.anyInt())).thenReturn(Optional.of(post));
        this.mockMvc.perform(get("/update?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post/update"));
    }

    @Test
    @WithMockUser
    void whenShowPostThenOk() throws Exception {
        Post post = Post.of(1, "Продаю машину ладу 01");
        Mockito.when(postService.findPostById(Mockito.anyInt())).thenReturn(Optional.of(post));
        this.mockMvc.perform(get("/show?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post"));
    }

    @Test
    @WithMockUser
    void whenAddCommentThenOk() throws Exception {
        Post post = Post.of(1, "Продаю машину ладу 01");
        Mockito.when(postService.findPostById(Mockito.anyInt())).thenReturn(Optional.of(post));
        this.mockMvc.perform(get("/addComment?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("comment/create"));
    }

    @Test
    @WithMockUser
    void whenSavePostThenRedirect() throws Exception {
        this.mockMvc.perform(post("/save")
                .param("name", "Куплю ладу-грант. Дорого."))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(postService).savePost(argument.capture());
        assertThat(argument.getValue().getName(), is("Куплю ладу-грант. Дорого."));
    }

    @Test
    @WithMockUser
    void whenSaveCommentThenOk() throws Exception {
        Post post = Post.of(1, "Продаю машину ладу 01");
        Mockito.when(postService.findPostById(Mockito.anyInt())).thenReturn(Optional.of(post));
        this.mockMvc.perform(post("/saveComment?id=1")
                .param("text", "Новый комментарий"))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<Comment> argument = ArgumentCaptor.forClass(Comment.class);
        verify(commentService).saveComment(argument.capture());
        assertThat(argument.getValue().getText(), is("Новый комментарий"));
    }
}
