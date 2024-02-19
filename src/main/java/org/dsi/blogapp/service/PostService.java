package org.dsi.blogapp.service;

import org.dsi.blogapp.exception.ResourceNotFoundException;
import org.dsi.blogapp.model.Category;
import org.dsi.blogapp.model.Post;
import org.dsi.blogapp.model.User;
import org.dsi.blogapp.payload.PostDto;
import org.dsi.blogapp.repository.CategoryRepository;
import org.dsi.blogapp.repository.PostRepository;
import org.dsi.blogapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public PostService(ModelMapper modelMapper,
                       PostRepository postRepository,
                       CategoryRepository categoryRepository,
                       UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(PostDto postDto, int userId, int categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Post post = modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        post.setUser(user);
        post.setImageName("default.png");
        post.setLocalDateTime(LocalDateTime.now());

        return postRepository.save(post);
    }

    public Post updatePost(PostDto postDto, int postId) {
        return null;
    }
    public void deletePost(int postId) {

    }
    public List<Post> getAllPost() {
        return null;
    }
    public Post getPostById(int postId) {
        return null;
    }
    public List<Post> getPostsByCategory(int categoryId) {
        return null;
    }
    public List<Post> getPostsByUser(int userId) {
        return null;
    }

}
