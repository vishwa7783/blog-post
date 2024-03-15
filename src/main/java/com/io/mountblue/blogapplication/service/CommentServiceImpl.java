package com.io.mountblue.blogapplication.service;

import com.io.mountblue.blogapplication.dao.CommentRepository;
import com.io.mountblue.blogapplication.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(int id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment findCommentById(int id) {
        Optional<Comment> result = commentRepository.findById(id);
        Comment comment = null;
        if(result.isPresent()){
            comment = result.get();
        }
        else {
            throw new RuntimeException("Did not find comment id - " + id);
        }
        return comment;
    }
}
