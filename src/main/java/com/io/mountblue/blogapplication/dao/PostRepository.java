package com.io.mountblue.blogapplication.dao;

import com.io.mountblue.blogapplication.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
