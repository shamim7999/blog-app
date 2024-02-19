package org.dsi.blogapp.controller;

import jakarta.validation.Valid;
import org.dsi.blogapp.payload.ApiResponse;
import org.dsi.blogapp.payload.CategoryDto;
import org.dsi.blogapp.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/{categoryDtoId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable int categoryDtoId) {
        return new ResponseEntity<>(categoryService.getCategory(categoryDtoId), HttpStatus.OK);
    }

    @PutMapping("/{categoryDtoId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable int categoryDtoId) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto, categoryDtoId),
                HttpStatus.OK);
    }

    @DeleteMapping("/{categoryDtoId}")
    public ResponseEntity<ApiResponse> hardDeleteCategory(@PathVariable int categoryDtoId) {
        categoryService.hardDeleteCategory(categoryDtoId);
        return new ResponseEntity<>(
                new ApiResponse("Category for ID: "+categoryDtoId+" Deleted Successfully.", true),
                HttpStatus.OK
        );
    }
}
