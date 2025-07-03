package com.example.projeto_spring_boot_user.dto;

public record AuthenticationDTO(
        String email,
        String password
) {
}
