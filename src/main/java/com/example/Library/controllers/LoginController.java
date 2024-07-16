package com.example.Library.controllers;

import com.example.Library.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    @Autowired
    public LoginController(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    @GetMapping
    public String showLoginForm(){
        return "login";
    }
    @PostMapping
    public String loginUser(@ModelAttribute LoginDto loginDto, Model model){
        try{
            Authentication authentication = authenticationManager.authenticate( //об'єкт аутентифікації користувача
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword())
            );
            return "redirect:/";
        }catch (Exception e){
            model.addAttribute("error","Invalid username or password");
            return "login";
        }
    }
}
