package com.io.mountblue.blogapplication.service;

import com.io.mountblue.blogapplication.dao.CommentRepository;
import com.io.mountblue.blogapplication.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;

public interface CommentService {
    void saveComment(Comment comment);
}
