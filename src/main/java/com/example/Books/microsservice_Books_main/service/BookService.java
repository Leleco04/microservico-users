package com.example.Books.microsservice_Books_main.service;

import com.example.Books.microsservice_Books_main.entity.Book;
import com.example.Books.microsservice_Books_main.enums.ContentRatingEnum;
import com.example.Books.microsservice_Books_main.exception.ResourceNotFoundException;
import com.example.Books.microsservice_Books_main.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class BookService {

    public final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(BookService.class.getName()); //cria o logger

    @Autowired
    private BookRepository bookRepository;

    private static String UPLOAD_DIR = "uploads/";

    //função para registro de livros
    public Book register(Book book) {
        logger.info("Book registred."); //logger para fins de confirmação através do console
        return bookRepository.save(book);
    }

    //função para edição de livros
    public Book edit(Book book) {
        logger.info("The informations has been edited."); //logger para fins de confirmação através do console
        Book entity = bookRepository.findById(book.getId()).
                orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        entity.setAuthor(book.getAuthor());
        entity.setTitle(book.getTitle());
        entity.setPublishedYear(book.getPublishedYear());
        entity.setGender(book.getGender());

        return bookRepository.save(book);
    }

    //função para remoção de livros do sistema
    public void remove(Long id) {
        logger.info("Book removed."); //logger para fins de confirmação através do console
        Book entity = bookRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Book not found!"));

        bookRepository.delete(entity);
    }

    //função de localização do livro pelo id
    public Book findBookById(Long id) {
        logger.info("Book found."); //logger para fins de confirmação através do console
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found!"));
    }

    //função de localização de todos os livros cadastrados
    public List<Book> listAllBooks() {
        logger.info("Books found."); //logger para fins de confirmação através do console
        return bookRepository.findAll();
    }

    //função de localização de todos os livros filtrados por classificação indicativa ou por título
    public List<Book> listBooksByContentRatingOrTitle(String contentRating, String title) {
        logger.info("Books found."); //logger para fins de confirmação através do console
        if (contentRating != null && !contentRating.isEmpty()) {
            ContentRatingEnum contentRatingEnum = ContentRatingEnum.valueOf(contentRating.toUpperCase());
            return bookRepository.findBooksByContentRating(contentRatingEnum);
        }
        if (title != null && !title.trim().isEmpty()) {
            return bookRepository.findBooksByTitleContainingIgnoreCase(title);
        }
        return bookRepository.findAll();
    }

    @Transactional
    public Book registerWithImage(Book book, String contentRatingString, MultipartFile file) throws IOException {
        logger.info("Registering a new book.");

        if (StringUtils.hasText(contentRatingString)) {
            try {
                // usa o método auxiliar criado no Enum para converter a String
                ContentRatingEnum rating = ContentRatingEnum.fromString(contentRatingString);
                book.setContentRating(rating);
                logger.info("Content rating set to: " + rating);
            } catch (IllegalArgumentException e) {
                logger.severe("Invalid content rating value provided: " + contentRatingString);

                throw new ResourceNotFoundException("Invalid content rating value provided: " + contentRatingString);
            }
        }

        Book savedBook = bookRepository.save(book);
        logger.info("Book registered with ID: " + savedBook.getId());

        if (file != null && !file.isEmpty()) {
            logger.info("Image detected. Processing...");

            validateImageFile(file);

            Path imagePath = saveImageFile(file); //método que auxilia a savar a imagem
            savedBook.setImagePath(imagePath.toString()); //define o caminho da imagem

            logger.info("Saving image path to the book: " + imagePath);
            return bookRepository.save(savedBook);
        }
        return savedBook;
    }

    private Path saveImageFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }

        // limpa o nome do arquivo para evitar problemas de path traversal
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // gera um nome de arquivo único para evitar conflitos
        String uniqueFileName = UUID.randomUUID().toString() + extension;

        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath);

        return filePath;
    }

    public Book saveBookImage(Long id, MultipartFile file) throws IOException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found!"));
        Path imagePath = saveImageFile(file);
        book.setImagePath(imagePath.toString());
        return bookRepository.save(book);
    }

    private void validateImageFile(MultipartFile file) {
        // lista de MIME Types permitidos
        List<String> allowedMimeTypes = Arrays.asList("image/jpeg", "image/png");

        String fileMimeType = file.getContentType();

        if (fileMimeType == null || !allowedMimeTypes.contains(fileMimeType.toLowerCase())) {
            throw new IllegalArgumentException("Invalid image type provided.");
        }
    }
}