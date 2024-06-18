package com.levin.library.controller;


import com.levin.library.modal.Author;
import com.levin.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping()
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        Author savedAuthor = authorService.saveAuthor(author);
        return ResponseEntity.ok(savedAuthor);
    }

    @GetMapping()
    public List<Author> fetchAllAuthors() {
        try {
            return authorService.fetchAllAuthors();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch all authors: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> authorOptional = authorService.fetchAuthorById(id);
        return authorOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{authorId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long authorId, @RequestBody Author author) {
        Optional<Author> updatedAuthorOptional = authorService.updateAuthor(authorId, author);
        return updatedAuthorOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{authorId}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long authorId) {
        boolean deletionStatus = authorService.deleteAuthor(authorId);
        if (deletionStatus) {
            return ResponseEntity.ok("Author with ID " + authorId + " has been deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete author with ID " + authorId);
        }
    }
}
