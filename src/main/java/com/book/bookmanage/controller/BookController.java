package com.book.bookmanage.controller;

import com.book.bookmanage.entity.Book;
import com.book.bookmanage.entity.Category;
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
    /* Redirect to new book page along with categories list
     * books/new => redirect to a new page
     */
    @RequestMapping("/new")
    public String showNewBookPage(Model model, @ModelAttribute("category") Category category) {
        Book book = new Book();
        model.addAttribute("book", book);
        List<Category> list = new ArrayList<>();
        model.addAttribute("allCategory", list);
        return "add-book";
    }

    /*
     * Save entity model with its foreign reference
     * Entity i.e, Book and reference i.e, category
     * books/new => to create a new model object
     */
    @PostMapping(path = "/save")

    public ResponseEntity<String> saveNewBook(@RequestBody Book book) {

        if (book.getBookname().isEmpty() || Objects.isNull(book.getBookname()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else if (book.getAuthor().isEmpty() || Objects.isNull(book.getAuthor()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else {

            bookService.saveBook(book);

        }
        return ResponseEntity.ok(REDIRECT);
    }


    /*
     * Redirect to edit book page along with categories list
     * books/edit/{id} => redirect to a edit book page with selected id
     */
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookService.loadBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
        }
        return "edit-book";
    }


    /*
     * Update entity model with its foreign reference
     * Entity i.e, Book and reference i.e, category
     * books/update/{id} => to update existing model object
     */
    @PostMapping(path = "/update/{id}")
    public String updateBook(@PathVariable("id") int id, @RequestBody Book book) {

        bookService.updateBook(id, book);
        return REDIRECT;
    }


    /*
     * Delete entity model with its foreign reference
     * Entity i.e, Book and reference i.e, category
     * books/delete/{id} => to delete an existing model object
     */
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return REDIRECT;
    }

}