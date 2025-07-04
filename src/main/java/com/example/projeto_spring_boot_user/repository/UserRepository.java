package com.example.projeto_spring_boot_user.repository;

import com.example.projeto_spring_boot_user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    // Busca usuário por e-mail
    UserDetails findByEmail(String email);
}
