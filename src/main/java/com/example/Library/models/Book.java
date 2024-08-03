package com.example.Library.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "genre",nullable = false)
    private String genre;
    @Column(name = "language",nullable = false)
    private String language;
    @Temporal(TemporalType.DATE)
    @Column(name = "releaseDate",nullable = false)
    private Date releaseDate;
    @Column(name = "quantity",nullable = false)
    private Integer quantity;
    @Column(name = "is_available")
    private Boolean isAvailable;
    @Column(name = "description")
    private String description;
    @Column(name = "images_url")
    private List<String> imagesUrl;
}
