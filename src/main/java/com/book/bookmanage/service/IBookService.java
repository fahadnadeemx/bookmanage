package com.book.bookmanage.service;


import com.book.bookmanage.entity.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {

    List<Book> loadAllBooks();

    Optional<Book> loadBookById(int id);

    Book saveBook(Book book);

    Book updateBook(int id, Book book);

    void deleteBook(int id);
}



