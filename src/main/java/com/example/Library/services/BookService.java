package com.example.Library.services;

import com.example.Library.models.Book;
import com.example.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    public Optional<Book> findById(Long id){
        return bookRepository.findById(id);
    }
    public Book addBook(Book book){
        return bookRepository.save(book);
    }
    public void deleteById(Long id){
        bookRepository.deleteById(id);
    }
    public List<Book> findByName(String name){
        return bookRepository.findByName(name);
    }
    public List<Book> findByGenre(String genre){
        return bookRepository.findByGenre(genre);
    }
    public List<Book> findByLanguage(String language){
        return bookRepository.findByLanguage(language);
    }
    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }
}
