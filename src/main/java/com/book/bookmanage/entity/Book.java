package com.book.bookmanage.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Entity
public class Book {

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Id
    private int id;

    public Book(int id, String bookname, String author, int price, Category category) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.price = price;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name="Cid")
    private Category category;

    @NotNull
    @NotBlank(message = "Book Name is mandatory")
    private String bookname;

    @NotNull
    @NotBlank(message = "Author Name is mandatory")
    private String author;

    private int price;

    public void validate(Book book) {
        if (book.getBookname().isEmpty()) {
            throw new IllegalArgumentException("Book Name is Empty :" + book.getBookname() + " Book Name is empty");
        } else if (Objects.isNull(book.getBookname())) {
            throw new IllegalArgumentException("Book Name is Null :" + book.getBookname() + " Book Name is null");
        } else if (book.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Author is Empty :" + book.getAuthor() + " Author Name is empty");
        } else if (Objects.isNull(book.getAuthor())) {
            throw new IllegalArgumentException("Author is Null :" + book.getAuthor() + " Author is null");
        }
        else if (Objects.isNull(book.getCategory())) {
            throw new IllegalArgumentException("Category  is Null:" + book.getCategory() + " Category is null");
        }
    }
}

