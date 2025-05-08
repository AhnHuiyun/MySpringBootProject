package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

//    @Test
    @Disabled
    @Rollback(value = false)
    void testCreateBook() {
        Book book = new Book();

        book.setTitle("JPA 프로그래밍");
        book.setAuthor("박둘리");
        book.setIsbn("9788956746432");
        book.setPrice(35000);
        book.setPublishDate(LocalDate.of(2025, 4, 30));
        Book addBook = bookRepository.save(book);

        assertThat(addBook).isNotNull();
    }

    @Test
    void testFindByIsbn() {
        Optional<Book> optionalBook = bookRepository.findById(1L);
        if(optionalBook.isPresent()) {
            Book existCustomer = optionalBook.get();
            assertThat(existCustomer.getId()).isEqualTo(1L);
        }
        Optional<Book> optionalBook2 = bookRepository.findByIsbn("9788956746425");
        Book a001customer = optionalBook2.orElseGet(() -> new Book());

        Book notFoundBook = bookRepository.findByIsbn("9788956746000")
                .orElseGet(() -> new Book());
    }

    @Disabled
    void testFindByAuthor() {
        Optional<Book> optionalBook = bookRepository.findById(1L);
        if(optionalBook.isPresent()) {
            Book existBook = optionalBook.get();
            assertThat(existBook.getId()).isEqualTo(1L);
        }
        List<Book> bookList = bookRepository.findByAuthor("홍길동");
        Book book = bookList.get(0);

        List<Book> notFoundBook = bookRepository.findByAuthor("박길동");
        assertThat(notFoundBook).isEmpty();
    }

    @Test
    @Rollback(value = false)
    void testUpdateBook() {
        Book book = bookRepository.findById(1L)  //Optional<Customer>
                .orElseThrow(() -> new RuntimeException("Book Not Found"));
        book.setTitle("스프링");
    }

    @Test
    @Rollback(value = false)
    void testDeleteBook() {
        Book book = bookRepository.findById(1L)  //Optional<Customer>
                .orElseThrow(() -> new RuntimeException("Book Not Found"));
        bookRepository.delete(book);
    }

//    @Test
//    void testByNotFoundException() {
//        //<X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)
//        //Supplier 의 추상메서드 T get()
//        Book book = bookRepository.findByCustomerId("A001")
//                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
//        assertThat(book.getCustomerId()).isEqualTo("A001");
//    }

}
