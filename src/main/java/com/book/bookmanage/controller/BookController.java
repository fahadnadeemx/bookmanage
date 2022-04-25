package com.book.bookmanage.controller;

import com.book.bookmanage.entity.Book;
import com.book.bookmanage.service.BookService;
import com.book.bookmanage.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Inject;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final String REDIRECT = "redirect:/books";
    /* Injecting services of books in the controller */

    @Autowired
    private BookService bookService;

    @Autowired
    private IBookService iBookService;

    @Inject
    public BookController(BookService bookService, IBookService iBookService) {
        this.bookService = bookService;
        this.iBookService = iBookService;
    }

    /* Injecting services of category in the controller */

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> list = bookService.loadAllBooks();
        model.addAttribute("allBooks", list);
        return "books";
    }


}