package org.dsi.blogapp.controller;

import org.dsi.blogapp.payload.CommentDto;
import org.dsi.blogapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable int postId) {
        return new ResponseEntity<>(
                commentService.createComment(commentDto, postId),
                HttpStatus.CREATED
        );
    }
}
