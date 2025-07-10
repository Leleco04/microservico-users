package com.example.Books.microsservice_Books_main.repositories;

import com.example.Books.microsservice_Books_main.entity.Book;
import com.example.Books.microsservice_Books_main.enums.ContentRatingEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByContentRating(ContentRatingEnum contentRating);
    List<Book> findBooksByTitleContainingIgnoreCase(String title);
    boolean existsByTitle(String title);
}