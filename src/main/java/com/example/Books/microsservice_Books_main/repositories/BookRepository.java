package com.example.Books.microsservice_Books_main.repositories;

import com.example.Books.microsservice_Books_main.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {}