package com.book.bookmanage.repository;
import com.book.bookmanage.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Bookrepository extends JpaRepository<Book, Integer> {
}


