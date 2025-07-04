package com.example.projeto_spring_boot_user.dto;

// Usado ao fazer o login, para retornar o token
public record LoginResponseDTO (
        String token
) {
}
