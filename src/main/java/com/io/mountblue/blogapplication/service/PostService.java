package com.io.mountblue.blogapplication.service;
import com.io.mountblue.blogapplication.entity.Post;
import com.io.mountblue.blogapplication.entity.Tag;
import com.io.mountblue.blogapplication.entity.User;

import java.util.List;
import java.util.Set;


public interface PostService {
    void publish(Post post);

    List<Post> findAllPosts();

    Post findPostById(int id);

    void deletePost(Post post);

    List<Post> getPostsBySearchWithFilter(String field, Set<String> authors, Set<String> tagNames, String startDate, String endDate);

}
