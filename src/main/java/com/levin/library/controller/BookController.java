package com.levin.library.controller;


import com.levin.library.modal.Book;
import com.levin.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping()
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping()
    public ResponseEntity<Page<Book>> fetchAllBooks(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Book> books = bookService.fetchAllBooks(PageRequest.of(page, size));
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all books: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.fetchBookById(id);
        return bookOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book book) {
        Optional<Book> updatedBookOptional = bookService.updateBook(bookId, book);
        return updatedBookOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        boolean deletionStatus = bookService.deleteBook(id);
        if (deletionStatus) {
            return ResponseEntity.ok("Book with ID " + id + " has been deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete book with ID " + id);
        }
    }
}
