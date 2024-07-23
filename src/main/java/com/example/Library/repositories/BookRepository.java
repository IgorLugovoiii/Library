package com.example.Library.repositories;

import com.example.Library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByName(String name);
    List<Book> findByGenre(String genre);
    List<Book> findByLanguage(String language);
}