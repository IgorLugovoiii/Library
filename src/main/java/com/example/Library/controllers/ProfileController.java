package com.example.Library.controllers;

import com.example.Library.models.BorrowedBooksList;
import com.example.Library.models.User;
import com.example.Library.services.BorrowedBooksListService;
import com.example.Library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final BorrowedBooksListService borrowedBooksListService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public ProfileController(UserService userService, BorrowedBooksListService borrowedBooksListService
    ,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.borrowedBooksListService = borrowedBooksListService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
    @GetMapping("/changePassword")
    public String showChangePasswordForm(){
        return "changePassword";
    }
    @PostMapping("/changePassword")
    public String changePassword(Principal principal, Model model, @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword){
        String username = principal.getName();
        User user = userService.findByUsername(username);

        if(bCryptPasswordEncoder.matches(currentPassword,user.getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userService.updateUser(user);
            model.addAttribute("message", "Password successfully updated");
        } else {
            model.addAttribute("error", "Current password is incorrect");
        }

        return "changePassword";
    }
}
