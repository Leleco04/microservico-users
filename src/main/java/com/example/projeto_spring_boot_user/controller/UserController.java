package com.example.projeto_spring_boot_user.controller;

import com.example.projeto_spring_boot_user.exception.UserNotFoundException;
import com.example.projeto_spring_boot_user.domain.User;

import com.example.projeto_spring_boot_user.service.UserService;
import com.example.projeto_spring_boot_user.util.ConversorNumerico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
// Define o prefixo user na url
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Retorna todos os usuários cadastrados
    // localhost:8080/user
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        // Retorna todos os usuários usando o findAll()
        return ResponseEntity.ok(userService.findAll());
    }

    // Retorna o usuário de acordo com o id
    // localhost:8080/user/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) throws UserNotFoundException {
        Long idFormatado = Long.valueOf(ConversorNumerico.formatarId(id));

        // Pega o usuário pelo id (findById)
        User user = userService.findById(idFormatado);

        // Retorna o usuário após encontrar
        return ResponseEntity.ok(user);
    }

    // Deleta um usuário cadastrado
    // localhost:8080/user/delete/{id} *METHOD DELETE*
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") String id) {
        Long idFormatado = Long.valueOf(ConversorNumerico.formatarId(id));
        try {
            userService.deleteById(idFormatado);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}