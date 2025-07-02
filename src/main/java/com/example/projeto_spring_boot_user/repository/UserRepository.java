package com.example.projeto_spring_boot_user.repository;

import com.example.projeto_spring_boot_user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
