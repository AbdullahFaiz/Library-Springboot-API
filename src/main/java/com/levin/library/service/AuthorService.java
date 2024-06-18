package com.levin.library.service;

import com.levin.library.modal.Author;
import com.levin.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author saveAuthor(Author author){
        try {
            return authorRepository.save(author);
        }catch (Exception ex){
            throw new RuntimeException("Failed to save Author: "+ ex.getMessage() );
        }
    }

    public List<Author> fetchAllAuthors(){
        try{
            return authorRepository.findAll();
        }catch (Exception ex){
            throw new RuntimeException("Failed to fetch all authors: "+ ex.getMessage() );
        }
    }

    public Optional<Author> fetchAuthorById(Long id) {
        try {
            return authorRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch author by ID: " + e.getMessage());
        }
    }

    public Optional<Author> updateAuthor(Long id, Author updatedAuthor) {
        try {
            Optional<Author> existingAuthorOptional = authorRepository.findById(id);
            if (existingAuthorOptional.isPresent()) {
                Author existingAuthor = existingAuthorOptional.get();
                existingAuthor.setFirstName(updatedAuthor.getFirstName());
                existingAuthor.setLastName(updatedAuthor.getLastName());
                Author savedEntity = authorRepository.save(existingAuthor);
                return Optional.of(savedEntity);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update author: " + e.getMessage());
        }
    }

    public boolean deleteAuthor(Long id) {
        try {
            authorRepository.deleteById(id);
            return true; // Deletion successful
        } catch (Exception e) {
            // Handle exception or log the error
            throw new RuntimeException("Failed to delete author: " + e.getMessage());
        }
    }
}
