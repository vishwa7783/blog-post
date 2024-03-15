package com.io.mountblue.blogapplication.controller;

import com.io.mountblue.blogapplication.entity.Comment;
import com.io.mountblue.blogapplication.entity.Post;
import com.io.mountblue.blogapplication.service.CommentService;
import com.io.mountblue.blogapplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class CommentController {
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @GetMapping("/post/comment/{postId}")
    public String addComment(@PathVariable("postId")int id, Model model){
        Post post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "comment";
    }

    @PostMapping("/savecomment")
    public String saveComment(@RequestParam("postId")int id, @ModelAttribute("post")Post post, @RequestParam("commentText")String comment,Model model){
        Comment theComment = new Comment("anonymous","anonymous@gmail.com",comment);
        post.addComment(theComment);
        theComment.setPostId(id);
        theComment.setCreatedAt(String.valueOf(LocalDateTime.now()));
        theComment.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        commentService.saveComment(theComment);
        return "redirect:/";
    }
}
