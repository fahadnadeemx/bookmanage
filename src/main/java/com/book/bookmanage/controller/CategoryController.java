package com.book.bookmanage.controller;

import com.book.bookmanage.entity.Category;
import com.book.bookmanage.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<String> getAllCategory(Model model) {
        List<Category> list = categoryService.loadAllCategory();
        model.addAttribute("allCategory", list);
        return ResponseEntity.ok("categories");
    }


    @RequestMapping("/new")
    public String showNewCategoryPage(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "add-category";
    }

    @PostMapping(path = "/save")
    public ResponseEntity<String> saveNewCategory(@RequestBody Category category) {

        if (category.getName().isEmpty() || Objects.isNull(category.getName()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            categoryService.saveCategory(category);

        return ResponseEntity.ok("redirect:/index");
    }


    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") int id, Model model) {
        Optional<Category> category = categoryService.loadCategoryById(id);
        if(category.isPresent())
        {
            model.addAttribute("category", category);
        }
        return "edit-category";

    }

    @PostMapping(path = "/update/{id}")
    public String updateCategory(@PathVariable("id") int id, @RequestBody Category category) {
        category.setId(id);
        categoryService.updateCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
