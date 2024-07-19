package com.example.Library.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "borrowed_books_list")
public class BorrowedBooksList {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "reader_id",nullable = false)
    private User user;
    @ManyToMany
    @JoinTable(
            name = "borrowed_books",
            joinColumns = @JoinColumn(name = "borrowed_books_list_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )// створюємо проміжну таблицю, щоб нормалізувати таблиці і позбутися зв'язку багато до багатьох
    private List<Book> bookList;
    @Temporal(TemporalType.DATE)
    @Column(name = "taken_date")
    private Date takenDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "return_date")
    private Date returnDate;
    @Column(name = "max_term")
    private Integer maxTerm;
    @Column(name = "quantity",nullable = false)
    private Integer quantity;
}
