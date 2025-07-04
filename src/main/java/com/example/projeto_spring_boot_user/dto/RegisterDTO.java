package com.example.projeto_spring_boot_user.dto;

import com.example.projeto_spring_boot_user.util.UserRole;

// Usado para cadastrar o usuário novo
public record RegisterDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        UserRole role
) {
}
