package org.dsi.blogapp.service;

import org.dsi.blogapp.exception.ResourceNotFoundException;
import org.dsi.blogapp.model.Category;
import org.dsi.blogapp.model.Post;
import org.dsi.blogapp.model.User;
import org.dsi.blogapp.payload.CategoryDto;
import org.dsi.blogapp.payload.PostDto;
import org.dsi.blogapp.payload.UserDto;
import org.dsi.blogapp.repository.CategoryRepository;
import org.dsi.blogapp.repository.PostRepository;
import org.dsi.blogapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public PostDto createPost(PostDto postDto, int userId, int categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Post post = modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        post.setUser(user);
        post.setImageName("default.png");
        post.setLocalDateTime(LocalDateTime.now());

        post = postRepository.save(post);

        postDto = modelMapper.map(post, PostDto.class);
        postDto.setCategoryDto(modelMapper.map(post.getCategory(), CategoryDto.class));
        postDto.setUserDto(modelMapper.map(post.getUser(), UserDto.class));

        return postDto;
        //return modelMapper.map(postRepository.save(post), PostDto.class);
    }

    public PostDto updatePost(PostDto postDto, int postId) {
        return null;
    }
    public void hardDeletePost(int postId) {
        postRepository.delete(postRepository.findById(postId)
                .orElseThrow( () -> new ResourceNotFoundException("Post", "id", postId)));
    }
    public List<PostDto> getAllPost() {
        return postRepository.findAll()
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
    public PostDto getPostById(int postId) {
        return modelMapper.map(postRepository.findById(postId).orElseThrow(() ->
                                new ResourceNotFoundException("Post", "id", postId)
                        ),
                PostDto.class);
    }
    public List<PostDto> getPostsByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow( () -> new ResourceNotFoundException("Category", "id", categoryId) );
        return postRepository.findByCategory(category)
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
    public List<PostDto> getPostsByUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return postRepository.findByUser(user).stream().map(post -> {
            PostDto postDto = modelMapper.map(post, PostDto.class);
            postDto.setCategoryDto(modelMapper.map(post.getCategory(), CategoryDto.class));
            postDto.setUserDto(modelMapper.map(post.getUser(), UserDto.class));
            return postDto;
        }).collect(Collectors.toList());
    }

}
