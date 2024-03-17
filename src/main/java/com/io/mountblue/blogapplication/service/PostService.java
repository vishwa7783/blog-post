package com.io.mountblue.blogapplication.service;
import com.io.mountblue.blogapplication.entity.Post;
import com.io.mountblue.blogapplication.entity.Tag;
import com.io.mountblue.blogapplication.entity.User;

import java.util.List;


public interface PostService {
    void publish(Post post);

    List<Post> getPosts();

    Post findPostById(int id);

    void deletePost(Post post);

    List<Post> findAllPostSortedByDate();

    List<Post> findAllPostSortedByTitle();

    List<Post> findBySearchField(String authorName, String title, String tagName, String content);
}
