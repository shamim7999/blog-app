package org.dsi.blogapp.service;

import org.dsi.blogapp.exception.ResourceNotFoundException;
import org.dsi.blogapp.model.Comment;
import org.dsi.blogapp.model.Post;
import org.dsi.blogapp.payload.CommentDto;
import org.dsi.blogapp.repository.CommentReository;
import org.dsi.blogapp.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final PostRepository postRepository;
    private final CommentReository commentReository;
    private final ModelMapper modelMapper;

    public CommentService(PostRepository postRepository,
                          CommentReository commentReository,
                          ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentReository = commentReository;
        this.modelMapper = modelMapper;
    }

    public CommentDto createComment(CommentDto commentDto, int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        commentReository.save(comment);
        return modelMapper.map(comment, CommentDto.class);
    }
}
