package com.example.partitioning_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public String createPost(@RequestBody Post post) {
        postService.savePost(post);
        return "Post saved successfully!";
    }
}
