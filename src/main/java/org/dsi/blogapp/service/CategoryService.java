package org.dsi.blogapp.service;

import org.dsi.blogapp.exception.ResourceNotFoundException;
import org.dsi.blogapp.model.Category;
import org.dsi.blogapp.payload.CategoryDto;
import org.dsi.blogapp.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    public CategoryDto getCategory(int categoryDtoId) {
        return modelMapper.map(categoryRepository.findById(categoryDtoId)
                        .orElseThrow( () -> new ResourceNotFoundException("Category", "id", categoryDtoId) ),
                CategoryDto.class);
    }

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(
                category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList()
        );
    }
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryDtoId) {
        Category category = categoryRepository.findById(categoryDtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryDtoId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }
    public void hardDeleteCategory(int categoryDtoId) {
        Category category = categoryRepository.findById(categoryDtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryDtoId));
        categoryRepository.delete(category);
    }
}
