package com.io.mountblue.blogapplication.dao;

import com.io.mountblue.blogapplication.entity.Post;
import com.io.mountblue.blogapplication.entity.Tag;
import com.io.mountblue.blogapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByPublishedAtDesc();

    List<Post> findAllByOrderByTitle();

    List<Post> findAllByAuthorNameContainingOrTitleContainingOrTagsNameContainingOrContentContaining(String authorName, String title, String tagName, String content);

}
