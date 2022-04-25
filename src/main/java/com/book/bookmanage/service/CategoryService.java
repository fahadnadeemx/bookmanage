package com.book.bookmanage.service;


import com.book.bookmanage.entity.Category;
import com.book.bookmanage.repository.Categoryrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{

    @Autowired
    Categoryrepository categoryRepository;

    @Override
    public List<Category> loadAllCategory(){
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> loadCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

}
