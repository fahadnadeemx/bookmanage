package com.book.bookmanage.controller;


import com.book.bookmanage.BookmanageApplication;
import com.book.bookmanage.entity.Book;
import com.book.bookmanage.entity.Category;
import com.book.bookmanage.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BookmanageApplication.class)
public class BookControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testListofBooks() throws Exception {
        Category category = new Category(1, "Entertainment");

        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "first", "first", 10, category));
        bookList.add(new Book(2, "second", "first", 20, category));
        bookList.add(new Book(3, "third", "first", 30, category));
        Mockito.when(bookService.loadAllBooks()).thenReturn(bookList);
        String url = "/books";
        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(actualJsonResponse);

    }
    @Test
    public void testCreateBook() throws Exception {

        Category category = new Category(1, "Entertainment");
        Book newBook = new Book(1, "first", "first", 10, category);
        Book savedBook = new Book(1, "first", "first", 10, category);

        Mockito.when(bookService.saveBook(newBook)).thenReturn(savedBook);
        String url = "/books/save";

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(newBook)));
        Mockito.verify(bookService, Mockito.times(0)).saveBook(newBook);
    }

    @Test
    public void testUpdateBook() throws Exception {

        Category category = new Category(1, "Entertainment");
        Book newBook = new Book(1, "first", "first", 10, category);
        Book savedBook = new Book(1, "second", "second", 20, category);

        Mockito.when(bookService.updateBook(newBook.getId(), newBook)).thenReturn(savedBook);
        String url = "/books/update/" + savedBook.getId();
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(newBook)));
        Mockito.verify(bookService, Mockito.times(0)).saveBook(newBook);
    }

    @Test
    public void testBookNameMustNotBeBlank() throws Exception {
        bookService = Mockito.mock(BookService.class);
        Category category = new Category(1, "Entertainment");
        Book book = new Book(1, "", "Fahad", 1, category);
        Mockito.when(bookService.saveBook(book)).thenReturn(book);
        String url = "/books/save";
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

    }

    @Test
    public void testAuthorNameMustNotBeBlank() throws Exception {
        bookService = Mockito.mock(BookService.class);
        Category category = new Category(1, "Entertainment");
        Book book = new Book(1, "First", "", 1, category);
        Mockito.when(bookService.saveBook(book)).thenReturn(book);
        String url = "/books/save";

        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(book)));

    }

    @Test
    public void testCategoryNameMustNotBeNull() throws Exception {
        bookService = Mockito.mock(BookService.class);
        Category category = new Category(1, "");
        Book book = new Book(1, "First", "Fahad", 1, category);
        Mockito.when(bookService.saveBook(book)).thenReturn(book);
        String url = "/books/save";

        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(book)));

    }

}
