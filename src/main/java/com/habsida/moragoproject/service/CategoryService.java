package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Category;
import com.habsida.moragoproject.model.input.CategoryInput;
import com.habsida.moragoproject.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Page<Category> findAllPaged(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest);
    }

    public Category findById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category was not found by Id: " + id));
    }

    public Category create(CategoryInput categoryInput){
        Category category = new Category();

        if (categoryInput.getIsActive() == null) {
            category.setIsActive(false);
        } else {
            category.setIsActive(categoryInput.getIsActive());
        }
        if (categoryInput.getName() != null && !categoryInput.getName().isEmpty()) {
            category.setName(categoryInput.getName());
        } else {
            category.setName("");
        }

        return categoryRepository.save(category);
    }

    public Boolean delete(Long id){
        categoryRepository.deleteById(id);
        return true;
    }

    public Category update(Long id, CategoryInput categoryInput){
        Category category = findById(id);

        if (categoryInput.getIsActive() != null) {
            category.setIsActive(categoryInput.getIsActive());
        }

        if (categoryInput.getName() != null && !category.getName().isEmpty()) {
            category.setName(categoryInput.getName());
        }

        return categoryRepository.save(category);
    }
}
