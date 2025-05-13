package com.rookies3.myspringbootlab.service;

import com.rookies3.myspringbootlab.controller.dto.BookDTO;
import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.BookDetailRepository;
import com.rookies3.myspringbootlab.repository.BookRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;

    public List<BookDTO.Response> getAllBooks() {
        List<BookDTO.Response> books = bookRepository.findAll().stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
        return books;
    }

    public BookDTO.Response getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found", HttpStatus.NOT_FOUND));
        return BookDTO.Response.fromEntity(book);
    }

    public List<BookDTO.Response> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbnWithBookDetail(isbn).stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    public List<BookDTO.Response> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
    }

    public List<BookDTO.Response> getBooksByTitle(String title) {
        List<BookDTO.Response> books = bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(BookDTO.Response::fromEntity)
                .toList();
        return books;
    }

    @Transactional
    public BookDTO.Response createBook(BookDTO.Request request) {
        BookDetail detail = BookDetail.builder()
                .description(request.getDetailRequest().getDescription())
                .language(request.getDetailRequest().getLanguage())
                .pageCount(request.getDetailRequest().getPageCount())
                .publisher(request.getDetailRequest().getPublisher())
                .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                .edition(request.getDetailRequest().getEdition())
                .build();

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .bookDetail(detail)
                .build();

        detail.setBook(book);

        Book saved = bookRepository.save(book);
        return BookDTO.Response.fromEntity(saved);
    }

    @Transactional
    public BookDTO.Response updateBook(Long id, BookDTO.Request request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found", HttpStatus.NOT_FOUND));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        BookDetail detail = book.getBookDetail();
        BookDTO.BookDetailDTO detailDTO = request.getDetailRequest();

        detail.setDescription(detailDTO.getDescription());
        detail.setLanguage(detailDTO.getLanguage());
        detail.setPageCount(detailDTO.getPageCount());
        detail.setPublisher(detailDTO.getPublisher());
        detail.setCoverImageUrl(detailDTO.getCoverImageUrl());
        detail.setEdition(detailDTO.getEdition());

        return BookDTO.Response.fromEntity(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found", HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
    }
}
