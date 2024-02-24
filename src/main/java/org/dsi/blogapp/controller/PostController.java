package org.dsi.blogapp.controller;

import org.dsi.blogapp.model.Post;
import org.dsi.blogapp.payload.ApiResponse;
import org.dsi.blogapp.payload.PostDto;
import org.dsi.blogapp.service.PostService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable int userId) {
        return new ResponseEntity<>(postService.getPostsByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable int categoryId) {
        return new ResponseEntity<>(postService.getPostsByCategory(categoryId), HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(required = false) Optional<Integer> pageNumber,
                                                     @RequestParam(required = false) Optional<Integer> pageSize,
                                                     @RequestParam(required = false) Optional<String> sortBy ) {
        return new ResponseEntity<>(
                postService.getAllPost(pageNumber.orElse(1)-1,
                        pageSize.orElse(2),
                        Sort.by(sortBy.orElse("category_id")).descending()),
                HttpStatus.OK
        );
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostsByPostId(@PathVariable int postId) {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/categories/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable int userId,
                                              @PathVariable int categoryId) {
        return new ResponseEntity<>(postService.createPost(postDto, userId, categoryId),
                HttpStatus.CREATED);
    }
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int postId) {
        return new ResponseEntity<>(postService.updatePost(postDto, postId), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> hardDeletePost(@PathVariable int postId) {
        postService.hardDeletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post for Post Id: "+postId+" Deleted.", true),
                HttpStatus.OK);
    }

    @GetMapping("/posts/title/search")
    public ResponseEntity<List<PostDto>> getPostsByTitleContaining(@RequestParam String keyword) {
        return new ResponseEntity<>(
                new ArrayList<>(postService.findByTitleContaining(keyword)),
                HttpStatus.OK
        );
    }
}
