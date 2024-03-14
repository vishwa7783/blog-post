package com.io.mountblue.blogapplication.controller;

import com.io.mountblue.blogapplication.entity.Post;
import com.io.mountblue.blogapplication.entity.Tag;
import com.io.mountblue.blogapplication.entity.User;
import com.io.mountblue.blogapplication.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/")
    public String showPosts(Model model){
        List<Post> posts = postService.getPosts();
        model.addAttribute("posts",posts);
        return "show-posts";
    }

    @GetMapping("/newpost")
    public String showPublishForm(Model model){
        Post post = new Post();
        Tag tag = new Tag();
        model.addAttribute("post",post);
        model.addAttribute("tag",tag);
        return "publish-form";
    }

    @PostMapping("/publishform")
    public String publishForm(@ModelAttribute("tag") Tag tag,@ModelAttribute("post") Post post){
        User user = new User("v","v2@gmail.com","1234");

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(currentDate);

        tag.setUpdatedAt(date);
        post.setAuthor(user);
        post.setPublishedAt(date);
        post.setPublished(true);
        post.setUpdatedAt(date);
        post.setCreatedAt(date);
        post.setExcerpt(post.getContent());
        postService.publish(post);

        return "redirect:/";
    }

    @GetMapping("/post/{postId}")
    public String update(@PathVariable("postId")int id, Model model){
        Post post = postService.findPostById(id);
        model.addAttribute("post",post);
        return "post";
    }
}
