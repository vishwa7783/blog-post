package com.io.mountblue.blogapplication.service;

import com.io.mountblue.blogapplication.dao.PostRepository;
import com.io.mountblue.blogapplication.entity.Post;
import com.io.mountblue.blogapplication.entity.Tag;
import com.io.mountblue.blogapplication.entity.User;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public void publish(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post findPostById(int id) {
        Optional<Post> result = postRepository.findById(id);
        Post post = null;

        if (result.isPresent()) {
            post = result.get();
        }
        else {
            throw new RuntimeException("Did not find post id - " + id);
        }
        return post;
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Override
    public List<Post> findAllPostSortedByDate() {
        return postRepository.findAllByOrderByPublishedAtDesc();
    }

    @Override
    public List<Post> findAllPostSortedByTitle() {
        return postRepository.findAllByOrderByTitle();
    }

    @Override
    public List<Post> findBySearchField(String authorName, String title, String tagName, String content) {
        return postRepository.findAllByAuthorNameContainingOrTitleContainingOrTagsNameContainingOrContentContaining(authorName, title, tagName, content);
    }

}
