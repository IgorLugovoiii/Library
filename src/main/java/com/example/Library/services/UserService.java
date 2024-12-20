package com.example.Library.services;

import com.example.Library.models.User;
import com.example.Library.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }
    @Transactional
    public void createUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        emailService.sendSimpleMessage(
                user.getEmail(),
                "Registration Successful",
                "Thank you for registering with our library."
        );
    }
    public void updateUser(User user) {
        userRepository.save(user);
    }
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
