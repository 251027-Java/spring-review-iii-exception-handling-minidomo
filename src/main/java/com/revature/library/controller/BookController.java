package com.revature.library.controller;

import com.revature.library.exception.BookNotFoundException;
import com.revature.library.model.Book;
import com.revature.library.service.BookService;
import jakarta.validation.Valid;
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
        var book = bookService
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody @Valid Book book) {
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
}
