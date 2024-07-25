package com.example.Library.controllers;

import com.example.Library.models.Book;
import com.example.Library.models.BorrowedBooksList;
import com.example.Library.models.User;
import com.example.Library.services.BookService;
import com.example.Library.services.BorrowedBooksListService;
import com.example.Library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final UserService userService;
    private final BorrowedBooksListService borrowedBooksListService;
    @Autowired
    public BookController(BookService bookService,UserService userService,
                          BorrowedBooksListService borrowedBooksListService ){
        this.bookService = bookService;
        this.userService = userService;
        this.borrowedBooksListService = borrowedBooksListService;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    @GetMapping("/allBooks")
    public String findAllBooks(@RequestParam(value = "name", required = false) String name, Model model){
        List<Book> books;
        if(name != null && !name.isEmpty()){
            books = bookService.findByName(name);
        }
        else{
            books = bookService.findAllBooks();
        }
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
    @PostMapping("/borrow-book")
    public String borrowBook(@RequestParam Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails userDetails){
            User user = userService.findByUsername(userDetails.getUsername());
            Book book = bookService.findById(id).orElseThrow(()-> new IllegalArgumentException("Book not found"));
            if(book.getQuantity()>0){
                BorrowedBooksList borrowedBooksList = new BorrowedBooksList();
                borrowedBooksList.setUser(user);
                borrowedBooksList.setBookList(List.of(book));
                borrowedBooksList.setTakenDate(new Date(System.currentTimeMillis()));

                int maxTerm = 14;
                borrowedBooksList.setMaxTerm(maxTerm);

                Date returnDate = new Date(System.currentTimeMillis() + (maxTerm * 24 * 60 * 60 * 1000L));
                borrowedBooksList.setReturnDate(returnDate);

                Integer quantity = borrowedBooksList.getQuantity();
                if (quantity == null) {
                    quantity = 0;
                }
                borrowedBooksList.setQuantity(quantity + 1);

                borrowedBooksListService.save(borrowedBooksList);

                book.setQuantity(book.getQuantity() - 1);
                bookService.addBook(book);
            }
        }
        return "redirect:/book/allBooks";
    }
    @PostMapping("/return-book")
    public String returnBook(@RequestParam Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            User user = userService.findByUsername(userDetails.getUsername());
            Book book = bookService.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));

            List<BorrowedBooksList> borrowedBooksLists = borrowedBooksListService.findByUserAndBook(user, book);
            if (borrowedBooksLists != null && !borrowedBooksLists.isEmpty()) {
                BorrowedBooksList borrowedBooksList = borrowedBooksLists.get(0);
                borrowedBooksList.getBookList().remove(book);

                if (borrowedBooksList.getBookList().isEmpty()) {
                    borrowedBooksListService.delete(borrowedBooksList);
                } else {
                    borrowedBooksList.setQuantity(borrowedBooksList.getQuantity() - 1);
                    borrowedBooksListService.save(borrowedBooksList);
                }

                book.setQuantity(book.getQuantity() + 1);
                bookService.addBook(book);
            }
        }
        return "redirect:/book/allBooks";
    }

//    @GetMapping("/booksByName")
//    public String findByName(Model model, @RequestParam("name") String name){
//        List<Book> books = bookService.findByName(name);
//        model.addAttribute("books", books);
//        return "book/allBooks";
//    }
    @GetMapping("genre/{genre}")
    public List<Book> findByGenre(String genre){
        return bookService.findByGenre(genre);
    }
    @GetMapping("/language/{language}")
    public List<Book> findByLanguage(String language){
        return bookService.findByLanguage(language);
    }
}
