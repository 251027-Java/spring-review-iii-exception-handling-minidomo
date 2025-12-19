package com.revature.library.service;

import com.revature.library.exception.BookNotAvailableException;
import com.revature.library.exception.BookNotFoundException;
import com.revature.library.model.Book;
import com.revature.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book checkoutBook(Long bookId) {
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id: " + bookId + " not found"));

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book with id: " + bookId + " is not available");
        }

        book.setAvailable(false);

        return bookRepository.save(book);
    }

    @Transactional
    public Book returnBook(Long bookId) {
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id: " + bookId + " not found"));

        book.setAvailable(true);

        return bookRepository.save(book);
    }
}
