package com.merida.tecnm.market_backend_v2.domain.service;

import com.merida.tecnm.market_backend_v2.domain.Category;
import com.merida.tecnm.market_backend_v2.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    public Optional<Category> getCategory(int categoryId) {
        return categoryRepository.getCategory(categoryId);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> update(int categoryId, Category category) {
        return categoryRepository.getCategory(categoryId).map(existing -> {
            category.setCategoryId(categoryId);
            return categoryRepository.save(category);
        });
    }

    public boolean delete(int categoryId) {
        if (getCategory(categoryId).isPresent()) {
            categoryRepository.delete(categoryId);
            return true;
        }
        return false;
    }
}
