package com.example.projeto_spring_boot_user.service;

import com.example.projeto_spring_boot_user.exception.UserNotFoundException;
import com.example.projeto_spring_boot_user.domain.User;
import com.example.projeto_spring_boot_user.repository.UserRepository;
import com.example.projeto_spring_boot_user.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private boolean p = false;

    @Autowired
    private UserRepository repo;

    // Retorna todos os usuários
    public List<User> findAll() {
        return repo.findAll();
    }

    // Busca um usuário pelo id e o retorna
    public User findById(Long id) throws UserNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
    }

    // Deleta um usuário pelo id
    public void deleteById(Long id) throws UserNotFoundException {
        repo.deleteById(id);
    }

    // Cria o usuário
    public User create(User user) {
        return repo.save(user);
    }
}
