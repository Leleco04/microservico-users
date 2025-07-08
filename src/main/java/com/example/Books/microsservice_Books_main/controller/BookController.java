package com.example.Books.microsservice_Books_main.controller;

import com.example.Books.microsservice_Books_main.entity.Book;
import com.example.Books.microsservice_Books_main.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Book register(@RequestBody Book book) {
        return service.register(book);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Book edit(@RequestBody Book book) {
        return service.edit(book);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable("id") Long id) {
        service.remove(id);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Book searchBook(@PathVariable("id") Long id) {
        return service.searchBook(id);
    }

    @GetMapping
    public List<Book> listBooks() {
        return service.listBooks();
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/{id}")
    public ResponseEntity<Void> bookExists(@PathVariable Long id) {
        if (service.searchBook(id) != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}