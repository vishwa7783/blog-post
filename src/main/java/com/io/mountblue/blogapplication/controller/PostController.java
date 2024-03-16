package com.io.mountblue.blogapplication.controller;

import com.io.mountblue.blogapplication.entity.Post;
import com.io.mountblue.blogapplication.entity.Tag;
import com.io.mountblue.blogapplication.entity.User;
import com.io.mountblue.blogapplication.service.PostService;
import com.io.mountblue.blogapplication.service.TagService;
import com.io.mountblue.blogapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PostController {
    PostService postService;
    TagService tagService;
    UserService userService;

    public PostController(PostService postService,TagService tagService,UserService userService){
        this.postService = postService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showPosts(Model model){
        List<Post> posts = postService.getPosts();
        model.addAttribute("posts",posts);
        return "show-posts";
    }

    @GetMapping("/newpost")
    public String showPublishForm(Model model){
        Post post = new Post();
        model.addAttribute("post",post);
        model.addAttribute("presentPostId", 0);
        return "publish-form";
    }

    @PostMapping("/publishform")
    public String publishForm( @ModelAttribute("post") Post post,@RequestParam("tag") String tags, @ModelAttribute("presentPostId") int presentPostId){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(currentDate);

        List<String> tagsInPost = Arrays.asList(tags.split(","));
        List<Tag> tagsInDB = tagService.findAllTags();
        Set<String> tagsNameInDB = new HashSet<>();
        for (Tag tag : tagsInDB) {
            tagsNameInDB.add(tag.getName());
        }

        List<Tag> postTagList = new ArrayList<>();
        post.setTags(postTagList);

        for (String tag : tagsInPost) {
            if (!tagsNameInDB.contains(tag)) {
                Tag newTag = new Tag();
                newTag.setName(tag);
                tagService.saveTag(newTag);
                postTagList.add(newTag);
            } else {
                Tag newTag = tagService.findTagByName(tag);
                postTagList.add(newTag);
            }
        }

        StringBuilder excerpt = new StringBuilder();
        if (post.getContent().length() > 151) {
            excerpt.append(post.getContent(), 0, 150);
        } else {
            excerpt.append(post.getContent());
        }

        post.setTags(postTagList);
        User theUser = userService.findUserById(12);
        post.setAuthor(theUser);

        if(presentPostId == 0) {
            User user = new User("v", "v10@gmail.com", "1234");
            post.setPublishedAt(date);
            post.setUpdatedAt(date);
            post.setCreatedAt(date);

        }
        else{
            post.setUpdatedAt(date);
            post.setPublishedAt(post.getCreatedAt());
        }

        post.setExcerpt(excerpt.toString());
        post.setPublished(true);
        postService.publish(post);

        return "redirect:/";
    }

    @GetMapping("/post/{postId}")
    public String showSinglePost(@PathVariable("postId")int id, Model model){
        Post post = postService.findPostById(id);
        model.addAttribute("post",post);

        return "post";
    }

    @GetMapping("/update/{postId}")
    public String update(@PathVariable("postId")int id, Model model){
        Post post = postService.findPostById(id);

        List<Tag> tags = post.getTags();
        StringBuilder tagAsString = new StringBuilder();
        for(Tag tempTag : tags){
            tagAsString.append(tempTag.getName()).append(",");
        }

        if (!tagAsString.isEmpty()) {
            tagAsString.deleteCharAt(tagAsString.length() - 1);
        }

        model.addAttribute("tagsAsString",tagAsString);
        model.addAttribute("post",post);
        model.addAttribute("presentPostId",post.getId());

        return "publish-form";
    }

    @GetMapping("/delete/{postId}")
    public String deletePost(@PathVariable("postId")int id, Model model){
        Post post = postService.findPostById(id);
        postService.deletePost(post);
        return "redirect:/";
    }

    @PostMapping("/")
    public String sortPosts(@ModelAttribute("selectedOption") String selectedOption, Model model){
        if(selectedOption.equals("date")) {
            List<Post> posts = postService.findAllPostSortedByDate();
            model.addAttribute("posts", posts);
        }
        else if(selectedOption.equals("title")){
            List<Post> posts = postService.findAllPostSortedByTitle();
            model.addAttribute("posts", posts);
        }else{
            List<Post> posts = postService.getPosts();
            model.addAttribute("posts",posts);
        }
        return "show-posts";
    }
}
