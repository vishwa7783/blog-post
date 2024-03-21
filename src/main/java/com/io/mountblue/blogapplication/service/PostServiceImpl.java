package com.io.mountblue.blogapplication.service;

import com.io.mountblue.blogapplication.dao.PostRepository;
import com.io.mountblue.blogapplication.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public List<Post> findAllPosts() {
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
    public Page<Post> getPostsBySearchWithFilter(String field, Set<String> authors, Set<String> tagNames, String startDate, String endDate, int pageNo, String sortDir) {
        if (startDate != null && startDate.isEmpty()) {
            startDate = null;
        }
        if (endDate != null && endDate.isEmpty()) {
            endDate = null;
        }
        if(sortDir == null ){
            sortDir = "DESC";
        }
        String sortBy = "publishedAt";
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo - 1,2, sort);
        return postRepository.findAllBySearchWithFilters(field, authors, tagNames, startDate, endDate, pageable);
    }

    @Override
    public Page<Post> getPaginatedPosts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return postRepository.findAll(pageable);
    }
}
