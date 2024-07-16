package com.example.Library.controllers;

import com.example.Library.dto.RegistrationDto;
import com.example.Library.models.Role;
import com.example.Library.models.User;
import com.example.Library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserService userService;
    @Autowired
    public RegistrationController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("registrationDto", new RegistrationDto());
        return "registration";
    }
    @PostMapping
    public String registerUser(@ModelAttribute("registrationDto")RegistrationDto registrationDto, Model model){
        try{
            User user = new User();
            user.setUsername(registrationDto.getUsername());
            user.setPassword(registrationDto.getPassword());
            user.setFullName(registrationDto.getFullName());
            user.setEmail(registrationDto.getEmail());
            user.setRole(registrationDto.getRole() != null ? registrationDto.getRole() : Role.READER);
            userService.createUser(user);
            return "redirect:/login";
        }catch (Exception e){
            model.addAttribute("errorMessage","Failed to register user");
            return "registration";
        }
    }
}
