package com.levin.library.modal;


import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long bookId;
    private String name;
    private String isbn;
    @ManyToOne
    private Author author;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
