package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.Category;
import com.habsida.moragoproject.model.input.CategoryInput;
import com.habsida.moragoproject.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<Category> getAllCategory(){
        return categoryService.findAll();
    }

    @QueryMapping
    public Page<Category> getAllCategoryPaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return categoryService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public Category getCategoryById(@Argument Long id){
        return categoryService.findById(id);
    }

    @MutationMapping
    public Category createCategory(@Argument CategoryInput categoryInput){
        return categoryService.create(categoryInput);
    }

    @MutationMapping
    public Boolean deleteCategoryById(@Argument Long id){
        return categoryService.delete(id);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument CategoryInput categoryInput){
        return categoryService.update(id, categoryInput);
    }
}
