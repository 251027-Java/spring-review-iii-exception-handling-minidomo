package com.revature.library;

import com.revature.library.model.Book;
import com.revature.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner init(BookRepository bookRepository) {
        return args -> {
            List<Book> books = List.of(
                    new Book("Clean Code", "Robert Martin", "9780132350884"),
                    new Book("The Pragmatic Programmer", "David Thomas", "9780135957059"),
                    new Book("Design Patterns", "Gang of Four", "9780201633610"),
                    new Book("Effective Java", "Joshua Bloch", "9780134685991"),
                    new Book("Spring in Action", "Craig Walls", "9781617297571"));
            bookRepository.saveAll(books);
        };
    }
}
