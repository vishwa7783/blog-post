package com.io.mountblue.blogapplication.service;

import com.io.mountblue.blogapplication.dao.CommentRepository;
import com.io.mountblue.blogapplication.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }
}
