package com.book.bookmanage.service;

import com.book.bookmanage.entity.Category;
import com.book.bookmanage.repository.Categoryrepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private Categoryrepository categoryrepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getAllCategory() {
        List<Category> list = new ArrayList<Category>();
        Category category1 = new Category(1, "first");
        Category category2 = new Category(2, "second");
        list.add(category1);
        list.add(category2);
        when(categoryrepository.findAll()).thenReturn(list);

        List<Category> CategoryList = categoryService.loadAllCategory();

        assertEquals(2, CategoryList.size());

    }
    @Test
    public void test_getCategoryById_found() {
        //Arrange
        Category category1 = new Category(1, "first");
        when(categoryrepository.findById(1)).thenReturn(Optional.of(category1));

        Optional<Category> categoryList = categoryService.loadCategoryById(1);
        assertEquals(1, categoryList.get().getId());

    }
    @Test
    public void SaveCategory() {

        Category category1 = new Category(1, "first");
        when(categoryrepository.save(any(Category.class))).thenReturn(new Category());

        List<Category> categoryList = Collections.singletonList(categoryService.saveCategory(category1));
        assertEquals(1, categoryList.size());
    }
    @Test
    public void test_updatecategory() {
        Category replacement = spy(new Category(1, "first"));
        Category replaced = new Category(2, "saved");
        when(categoryrepository.save(any(Category.class)))
                .thenReturn(replaced);
        List<Category> categoryList = Collections.singletonList(categoryService.updateCategory(replacement));
        assertEquals(1, categoryList.size());
    }
}
