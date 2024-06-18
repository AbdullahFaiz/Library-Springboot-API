package com.levin.library.service;

import com.levin.library.modal.Book;
import com.levin.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(Book book){
        try {
            return bookRepository.save(book);
        }catch (Exception ex){
            throw new RuntimeException("Failed to save Book: "+ ex.getMessage() );
        }
    }

    public Page<Book> fetchAllBooks(Pageable pageable){
        try{
            return bookRepository.findAll(pageable);
        }catch (Exception ex){
            throw new RuntimeException("Failed to fetch all books: "+ ex.getMessage() );
        }
    }

    public Optional<Book> fetchBookById(Long id) {
        try {
            return bookRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch book by ID: " + e.getMessage());
        }
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        try {
            Optional<Book> existingBookOptional = bookRepository.findById(id);
            if (existingBookOptional.isPresent()) {
                Book existingBook = existingBookOptional.get();
                existingBook.setName(updatedBook.getName());
                existingBook.setIsbn(updatedBook.getIsbn());
                existingBook.setAuthor(updatedBook.getAuthor());
                Book savedEntity = bookRepository.save(existingBook);
                return Optional.of(savedEntity);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update book: " + e.getMessage());
        }
    }

    public boolean deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete book: " + e.getMessage());
        }
    }
}
