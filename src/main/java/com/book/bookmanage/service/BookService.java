package com.book.bookmanage.service;


import com.book.bookmanage.entity.Book;
import com.book.bookmanage.repository.Bookrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    @Autowired
    public Bookrepository bookrepository;

    public BookService(Bookrepository bookrepository) {
        this.bookrepository = bookrepository;
    }

    @Override
    public List<Book> loadAllBooks() {

        return bookrepository.findAll();
    }

    @Override
    public Optional<Book> loadBookById(int id) {
        return bookrepository.findById(id);
    }

    @Override
    public Book saveBook(Book book) {
        try
        {
            book.validate(book);
            return bookrepository.save(book);
        }catch (Exception exception)
        {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    @Override
    public Book updateBook(int id, Book book) {
        book.setId(id);
        return bookrepository.save(book);
    }

    @Override
    public void deleteBook(int id) {
        bookrepository.deleteById(id);
    }
}