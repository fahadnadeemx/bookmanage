package com.book.bookmanage.service;


import com.book.bookmanage.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

     List<Category> loadAllCategory();

     Optional<Category> loadCategoryById(int id) ;

     Category saveCategory(Category category);

     void deleteCategory(int id) ;

     Category updateCategory(Category category) ;

}
