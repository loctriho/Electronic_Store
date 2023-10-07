package com.triloc.webapp.electonicstore.controller;

import com.triloc.webapp.electonicstore.exception.ResourceNotFoundException;
import com.triloc.webapp.electonicstore.model.Category;
import com.triloc.webapp.electonicstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoriesController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<String> getAllCategoriesByName() {
        return categoryRepository.findAllWithoutProducts().stream()
                .map(Category::getCategory_name)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") Long categoryId)
            throws ResourceNotFoundException {
        Category category = findCategoryById(categoryId);
        return ResponseEntity.ok().body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long categoryId,
                                                   @RequestBody Category categoryDetails) throws ResourceNotFoundException {
        Category category = findCategoryById(categoryId);
        category.setCategory_name(categoryDetails.getCategory_name());
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteCategory(@PathVariable(value = "id") Long categoryId)
            throws ResourceNotFoundException {
        Category category = findCategoryById(categoryId);
        categoryRepository.delete(category);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private Category findCategoryById(Long categoryId) throws ResourceNotFoundException {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + categoryId));
    }
}
