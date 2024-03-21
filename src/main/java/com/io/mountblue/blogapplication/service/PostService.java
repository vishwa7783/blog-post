package com.io.mountblue.blogapplication.service;
import com.io.mountblue.blogapplication.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;


public interface PostService {
    void publish(Post post);

    List<Post> findAllPosts();

    Post findPostById(int id);

    void deletePost(Post post);

    Page<Post> getPostsBySearchWithFilter(String field, Set<String> authors, Set<String> tagNames, String startDate, String endDate, int pageNo, String sortDir);

    Page<Post> getPaginatedPosts(int pageNo, int pageSize);

}
