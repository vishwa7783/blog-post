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
    public String writeComment(@PathVariable("postId")int id, Model model){
        Post post = postService.findPostById(id);
        Integer presentCommentId = 0;
        model.addAttribute("post", post);
        model.addAttribute("presentCommentId", presentCommentId);
        return "comment";
    }

    @PostMapping("/savecomment")
    public String saveComment(@RequestParam("postId")int id,
                              @ModelAttribute("post")Post post, @RequestParam("commentText")String comment,
                             @ModelAttribute("presentCommentId") int presentCommentId){
        if(presentCommentId == 0) {
            Comment theComment = new Comment("anonymous", "anonymous@gmail.com", comment);
            post.addComment(theComment);
            theComment.setPostId(id);

            theComment.setCreatedAt(String.valueOf(LocalDateTime.now()));
            theComment.setUpdatedAt(String.valueOf(LocalDateTime.now()));

            commentService.saveComment(theComment);
        }else{
            Comment comment1 = commentService.findCommentById(presentCommentId);
            comment1.setComment(comment);
            comment1.setUpdatedAt(String.valueOf(LocalDateTime.now()));
            commentService.saveComment(comment1);
        }

        return "redirect:/post/" + id;
    }

    @GetMapping("/deletecomment/{commentId}/{commentPostId}")
    public String deleteComment(@PathVariable("commentId") int commentId,@PathVariable("commentPostId")int postId){
        commentService.deleteCommentById(commentId);

        return "redirect:/post/"+postId;
    }

    @GetMapping("/updatecomment/{commentId}/{commentPostId}")
    public String updateComment(@PathVariable("commentId") int commentId,@PathVariable("commentPostId")int postId,Model model){
        Comment comment = commentService.findCommentById(commentId);
        comment.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        Post post = postService.findPostById(postId);

        model.addAttribute("post", post);
        model.addAttribute("commentName", comment.getComment());
        model.addAttribute("presentCommentId", commentId);

        return "comment";
    }

}
