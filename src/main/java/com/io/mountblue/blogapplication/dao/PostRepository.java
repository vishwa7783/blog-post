package com.io.mountblue.blogapplication.dao;

import com.io.mountblue.blogapplication.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    public List<Post> findAllByOrderByPublishedAtDesc();

    public List<Post> findAllByOrderByTitle();


}
