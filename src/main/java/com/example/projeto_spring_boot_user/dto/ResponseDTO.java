package com.example.projeto_spring_boot_user.dto;

import com.example.projeto_spring_boot_user.util.UserRole;

// DTO de resposta
public record ResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        UserRole role
) {
}