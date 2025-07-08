package com.example.Books.microsservice_Books_main.service;

import com.example.Books.microsservice_Books_main.entity.Book;
import com.example.Books.microsservice_Books_main.exception.ResourceNotFoundException;
import com.example.Books.microsservice_Books_main.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class BookService {

    public final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository bookRepository;

    public Book register(Book book) {
        logger.info("Book registred.");
        return bookRepository.save(book);
    }

    public Book edit(Book book) {
        logger.info("The informations has been edited.");
        Book entity = bookRepository.findById(book.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        entity.setAuthor(book.getAuthor());
        entity.setTitle(book.getTitle());
        entity.setPublishedYear(book.getPublishedYear());
        entity.setGender(book.getGender());

        return bookRepository.save(book);
    }

    public void remove(Long id) {
        logger.info("Book removed.");
        Book entity = bookRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        bookRepository.deleteById(id);
    }

    public Book searchBook(Long id){
        logger.info("Book found.");
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found!"));
    }

    public List<Book> listBooks() {
        logger.info("Books found.");
        return bookRepository.findAll();
    }
}
