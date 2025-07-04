package com.example.projeto_spring_boot_user.dto;

// Usado para autenticação do usuário, apenas email e senha
public record AuthenticationDTO(
        String email,
        String password
) {
}
