package com.example.projeto_spring_boot_user.util;

// Enumeração para tipos de usuário
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
