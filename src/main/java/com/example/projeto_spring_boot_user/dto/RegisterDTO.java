package com.example.projeto_spring_boot_user.dto;

import com.example.projeto_spring_boot_user.util.UserRole;

import java.time.LocalDate;

public record RegisterDTO(
        String nome,
        String sobrenome,
        String email,
        String senha,
        LocalDate dataNascimento,
        UserRole role
) {
}
