package com.example.Library.controllers;

import com.example.Library.models.BorrowedBooksList;
import com.example.Library.models.User;
import com.example.Library.services.BorrowedBooksListService;
import com.example.Library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    public UserService userService;
    public BorrowedBooksListService borrowedBooksListService;
    @Autowired
    public ProfileController(UserService userService, BorrowedBooksListService borrowedBooksListService){
        this.userService = userService;
        this.borrowedBooksListService = borrowedBooksListService;
    }
    @GetMapping
    public String showProfile(Model model, Principal principal){
        String userName = principal.getName();
        User user = userService.findByUsername(userName);
        List<BorrowedBooksList> borrowedBooks = borrowedBooksListService.findByReader(user);

        model.addAttribute("user",user);
        model.addAttribute("borrowedBooks", borrowedBooks);

        return "profile";
    }
}
