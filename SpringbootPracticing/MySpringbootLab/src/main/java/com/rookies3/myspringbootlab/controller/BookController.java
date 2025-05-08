package com.rookies3.myspringbootlab.controller;

import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("도서를 찾을 수 없습니다", HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(book);
    }

    @GetMapping("/author/{author}")
    public List<Book> getBookByAuthor(@PathVariable String author) {
        return bookRepository.findByAuthor(author);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookRepository.save(book));
    }

    // 이 부분은 GPT 사용
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(book.getTitle());
                    existing.setAuthor(book.getAuthor());
                    existing.setIsbn(book.getIsbn());
                    existing.setPrice(book.getPrice());
                    existing.setPublishDate(book.getPublishDate());
                    return ResponseEntity.ok(bookRepository.save(existing));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
