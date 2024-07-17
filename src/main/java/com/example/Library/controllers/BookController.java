package com.example.Library.controllers;

import com.example.Library.models.Book;
import com.example.Library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping("/allBooks")
    public String findAllBooks(Model model){
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books",books);
        return "book/allBooks";
    }
    @GetMapping("/addBook")
    public String showAddBookForm(Model model){
        model.addAttribute("book",new Book());
        return "book/addBook";
    }
    @PostMapping("/addBook")
    public String addBook(@ModelAttribute Book book){
        bookService.addBook(book);
        return "redirect:/book/allBooks";
    }
    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model){
        model.addAttribute("id",id);
        return "book/deleteBook";
    }
    @PostMapping("/delete/{id}")
    public String deleteBookById(@PathVariable Long id){
        bookService.deleteById(id);
        return "redirect:/book/allBooks";
    }
    @GetMapping("/booksByName")
    public List<Book> findByName(String name){
        return bookService.findByName(name);
    }
    @GetMapping("genre/{genre}")
    public List<Book> findByGenre(String genre){
        return bookService.findByGenre(genre);
    }
    @GetMapping("/language/{language}")
    public List<Book> findByLanguage(String language){
        return bookService.findByLanguage(language);
    }
}
