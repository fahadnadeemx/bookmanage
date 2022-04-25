package com.book.bookmanage.repository;

import com.book.bookmanage.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Categoryrepository extends JpaRepository<Category, Integer> {
}
