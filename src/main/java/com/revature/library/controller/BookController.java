package com.revature.library.controller;

import com.revature.library.model.Book;
import com.revature.library.service.BookService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return ResponseEntity.of(bookService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(book));
    }

    @PutMapping("/{id}/checkout")
    public ResponseEntity<Book> checkoutBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.checkoutBook(id));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Book> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.returnBook(id));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Void> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
