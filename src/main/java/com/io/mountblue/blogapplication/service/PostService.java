package com.io.mountblue.blogapplication.service;
import com.io.mountblue.blogapplication.entity.Post;

import java.util.List;


public interface PostService {
    void publish(Post post);

    List<Post> getPosts();

    Post findPostById(int id);

    void deletePost(Post post);

    List<Post> findAllPostSortedByDate();

    List<Post> findAllPostSortedByTitle();
}
