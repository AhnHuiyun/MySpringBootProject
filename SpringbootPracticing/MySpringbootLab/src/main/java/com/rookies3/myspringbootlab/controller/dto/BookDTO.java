package com.rookies3.myspringbootlab.controller.dto;

import com.rookies3.myspringbootlab.entity.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class BookDTO {
    @Getter
    @Setter
    public static class BookCreateRequest {
        @NotBlank(message = "Title 은 필수 입력항목입니다.")
        private String title;

        @NotBlank(message = "Author 은 필수 입력항목입니다.")
        private String author;

        @NotBlank(message = "Isbn 은 필수 입력항목입니다.")
        private String isbn;

        @NotBlank(message = "Price 은 필수 입력항목입니다.")
        private Integer price;

        @NotBlank(message = "PublishDate 은 필수 입력항목입니다.")
        private LocalDate publishDate;

        public Book toEntity() {
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
            book.setPrice(price);
            book.setPublishDate(publishDate);
            return book;
        }
    }

    @Getter
    @Setter
    public static class BookUpdateRequest {
        @NotBlank(message = "Title 은 필수 입력항목입니다.")
        private String title;

        @NotBlank(message = "Author 은 필수 입력항목입니다.")
        private String author;

        @NotBlank(message = "Price 은 필수 입력항목입니다.")
        private Integer price;

        @NotBlank(message = "PublishDate 은 필수 입력항목입니다.")
        private LocalDate publishDate;
    }

    @Getter
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        public BookResponse from(Book book) {
            BookResponse response = new BookResponse();
            response.id = book.getId();
            response.title = book.getTitle();
            response.author = book.getAuthor();
            response.isbn = book.getIsbn();
            response.price = book.getPrice();
            response.publishDate = book.getPublishDate();
            return response;
        }
    }
}
