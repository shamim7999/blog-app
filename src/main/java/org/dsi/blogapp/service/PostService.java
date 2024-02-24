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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public PostDto postToPostDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setCategoryDto(modelMapper.map(post.getCategory(), CategoryDto.class));
        postDto.setUserDto(modelMapper.map(post.getUser(), UserDto.class));

        return postDto;
    }

    public Post postDtoToPost(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setLocalDateTime(LocalDateTime.now());
        return post;
    }

    public PostDto createPost(PostDto postDto, int userId, int categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        Post post = postDtoToPost(postDto);
        post.setCategory(category);
        post.setUser(user);
        return postToPostDto(postRepository.save(post));
    }

    public PostDto updatePost(PostDto postDto, int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow( () -> new ResourceNotFoundException("Post", "id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        postRepository.save(post);
        return postToPostDto(post);
    }
    public void hardDeletePost(int postId) {
        postRepository.delete(postRepository.findById(postId)
                .orElseThrow( () -> new ResourceNotFoundException("Post", "id", postId)));
    }
    public List<PostDto> getAllPost(int pageNumber, int pageSize, Sort sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortBy);
        return postRepository.findAll(pageable)
                .stream()
                .map(this::postToPostDto)
                .collect(Collectors.toList());
    }
    public PostDto getPostById(int postId) {
        return postToPostDto(postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId)));
    }
    public List<PostDto> getPostsByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow( () -> new ResourceNotFoundException("Category", "id", categoryId) );
        return postRepository.findByCategory(category)
                .stream()
                .map(this::postToPostDto)
                .collect(Collectors.toList());
    }
    public List<PostDto> getPostsByUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return postRepository.findByUser(user).stream()
                .map(this::postToPostDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> findByTitleContaining(String keyword) {
        return postRepository.findByTitleContaining("%"+keyword+"%").stream()
                .map(this::postToPostDto)
                .collect(Collectors.toList());
    }
}
