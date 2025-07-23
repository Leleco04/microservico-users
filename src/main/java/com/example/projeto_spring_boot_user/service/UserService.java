package com.example.projeto_spring_boot_user.service;

import com.example.projeto_spring_boot_user.dto.RegisterDTO;
import com.example.projeto_spring_boot_user.dto.ResponseDTO;
import com.example.projeto_spring_boot_user.exception.UserNotFoundException;
import com.example.projeto_spring_boot_user.domain.User;
import com.example.projeto_spring_boot_user.repository.UserRepository;
import com.example.projeto_spring_boot_user.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private boolean p = false;

    @Autowired
    private UserRepository repo;

    // Retorna todos os usuários
    public Page<ResponseDTO> findAll(Pageable pageable) {
        Page<User> usersPage = repo.findAll(pageable);

        return usersPage.map(user -> new ResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        ));
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
    public ResponseDTO create(RegisterDTO dto) {

        // Verifica se o e-mail já está cadastrado
        if (repo.findByEmail(dto.email()) != null) {
            return null;
        }

        // Criptografa a senha do usuário
        String bcryptPassword = new BCryptPasswordEncoder().encode(dto.password());

        // Salva o usuário
        User savedUser = this.repo.save(new User(dto.firstName(), dto.lastName(), dto.email(), bcryptPassword, dto.role()));

        // Retorna o usuário de resposta
        return new ResponseDTO(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getEmail(), savedUser.getRole());
    }
}
