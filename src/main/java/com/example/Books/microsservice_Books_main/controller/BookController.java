package com.example.Books.microsservice_Books_main.controller;

import com.example.Books.microsservice_Books_main.entity.Book;
import com.example.Books.microsservice_Books_main.repositories.BookRepository;
import com.example.Books.microsservice_Books_main.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
//define o prefixo "book" na url
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;

    // diretório onde as imagens serão salvas
    private static String UPLOAD_DIR = "uploads/";

    @Autowired
    private BookRepository bookRepository;

    //retorna o registro de um livro em sistema
    //localhost:8888/registrer
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Book> register(@RequestParam("title")  String title,
                                         @RequestParam("author") String author,
                                         @RequestParam("publishedYear") Integer publishedYear,
                                         @RequestParam("pagesQuantity") Integer pagesQuantity,
                                         @RequestParam("gender") String gender,
                                         @RequestParam("contentRating") String contentRating,
                                         @RequestParam("file") MultipartFile file) {
        try{
            //cria o objeto Book com os dados recebidos
            Book bookData = new Book();

            if (!StringUtils.hasText(title) || !StringUtils.hasText(author) || publishedYear == null || pagesQuantity == null || !StringUtils.hasText(gender) || !StringUtils.hasText(contentRating)) {
                throw new IllegalArgumentException("All fields are required!.");
            }

            if(bookRepository.existsByTitle(title)){
                throw new IllegalArgumentException("Title already exists!.");
            }

            bookData.setTitle(title);
            bookData.setAuthor(author);
            bookData.setPublishedYear(publishedYear);
            bookData.setPagesQuantity(String.valueOf(pagesQuantity));
            bookData.setGender(gender);

            //método que lida com o livro e a imagem
            Book registeredBook = service.registerWithImage(bookData,contentRating, file);

            URI url = URI.create("/book" + registeredBook.getId());
            return ResponseEntity.created(url).body(registeredBook);

        } catch (IOException e){
            return ResponseEntity.status(500).build();
        }
    }

    //retorna o livro editado
    //localhost:8888/edit
    @PutMapping(value = "/edit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> edit(@RequestBody Book book) {
        return ResponseEntity.ok(service.edit(book));
    }

    //faz a remoção do livro do sistema
    //localhost:8888/books/remove/{id}
    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }

    //retorna o livro pelo id localizado
    //localhost:8888/books/{id}
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> findBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findBookById(id));
    }

    //retorna os todos os livros cadastrados em sistema
    //localhost:8888/books/
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> listAllBooks() {
        return service.listAllBooks();
    }

    //retorna os todos os livros filtrados por classificação indicativa ou por título
    @GetMapping(value = "/filter")
    public List<Book> listBooksByContentRating(@RequestParam(required = false) String contentRating,
                                               @RequestParam(required = false) String title) {
        return service.listBooksByContentRatingOrTitle(contentRating, title);
    }

    @PostMapping("/{id}/file")
    public ResponseEntity<Book> uploadImage(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {
        try {
            Book updatedBook = service.saveBookImage(id, file); // Delega toda a lógica para o service
            return ResponseEntity.ok(updatedBook);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}