package com.example.Library.dto;

import com.example.Library.models.Role;
import lombok.Data;

@Data
public class RegistrationDto {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Role role;
}
