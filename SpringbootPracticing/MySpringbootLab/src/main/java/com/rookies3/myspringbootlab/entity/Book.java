package com.rookies3.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="books")
@Getter
@Setter
@DynamicUpdate
public class Book {
    //Primary Key, pk 값을 persistence provider 가 결정해라
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유니크한 값을 가져야 하고, Null 값을 허용하지 않음
    @Column(unique = true, nullable = false)
    private String title;

    //Null 값을 허용하지 않음
    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate publishDate;
}
