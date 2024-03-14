package com.io.mountblue.blogapplication.service;

import com.io.mountblue.blogapplication.dao.PostRepository;
import com.io.mountblue.blogapplication.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface PostService {
    void publish(Post post);

    List<Post> getPosts();

    Post findPostById(int id);

    void deletePost(Post post);
}
