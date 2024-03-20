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
import java.time.LocalDate;
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
        List<Post> posts = postService.findAllPosts();
        List<Tag> tags = tagService.findAllTags();
        List<User> authors = userService.findAuthors();

        model.addAttribute("authors", authors);
        model.addAttribute("tags",tags);
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
        List<Tag> postTagList = tagService.tagHandlerInDb(tags);

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
        List<User> authors = userService.findAuthors();

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
        model.addAttribute("tags", tags);
        model.addAttribute("authors", authors);

        return "publish-form";
    }

    @GetMapping("/delete/{postId}")
    public String deletePost(@PathVariable("postId")int id, Model model){
        Post post = postService.findPostById(id);
        postService.deletePost(post);
        return "redirect:/";
    }

    @GetMapping("/filter")
    public String applyFilter(@RequestParam(required = false) Set<String> tagNames,
                              @RequestParam(required = false) Set<String> authorsName, Model model,
                              @RequestParam(name="startDate", required = false) String startDate,
                              @RequestParam(name="endDate", required = false) String endDate,
                              @RequestParam("field") String field){
        if (startDate != null && startDate.isEmpty()) {
            startDate = null;
        }
        if (endDate != null && endDate.isEmpty()) {
            endDate = null;
        }

        List<Post> posts = postService.getPostsBySearchWithFilter(field, authorsName, tagNames, startDate, endDate);
        Set<Tag> tagsSet = new HashSet<>();
        Set<User> usersSet = new HashSet<>();

        for (Post post : posts) {
            tagsSet.addAll(post.getTags());
            usersSet.add(post.getAuthor());
        }

        List<User> users = new ArrayList<>(usersSet);
        List<Tag> tags = new ArrayList<>(tagsSet);

        model.addAttribute("field", field);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", posts);
        model.addAttribute("authors", users);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("tagNames", tagNames);
        model.addAttribute("authorsName", authorsName);

        return "show-posts";
    }
}
