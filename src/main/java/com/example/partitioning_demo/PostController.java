package com.example.partitioning_demo;

import com.example.partitioning_demo.model.Post;
import com.example.partitioning_demo.model.User;
import com.example.partitioning_demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    @RequestMapping("/posts")
    public String createPost(@RequestBody Post post) {
        postService.savePost(post);
        return "Post saved successfully!";
    }

    @PostMapping
    @RequestMapping("/users")
    public String saveUserAndPosts(@RequestBody User user) {
        postService.saveUserAndPosts(user);
        return "Post saved successfully!";
    }
}
