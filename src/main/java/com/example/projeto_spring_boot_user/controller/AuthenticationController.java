package com.example.projeto_spring_boot_user.controller;

import com.example.projeto_spring_boot_user.domain.User;
import com.example.projeto_spring_boot_user.dto.AuthenticationDTO;
import com.example.projeto_spring_boot_user.dto.LoginResponseDTO;
import com.example.projeto_spring_boot_user.dto.RegisterDTO;
import com.example.projeto_spring_boot_user.repository.UserRepository;
import com.example.projeto_spring_boot_user.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO dto) {
        // Pega o usuaario (e-mail) e senha do formulário de login enviado pelo usuário
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        // Faz a autenticação de e-mail e senha
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Gera o token
        var token = tokenService.generateToken((User) auth.getPrincipal());

        // Retorna a resposta 200 OK com o token gerado
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO dto) {
        // Verifica se o e-mail de registro já está cadastrado
        if(this.userRepository.findByEmail(dto.email()) != null) return ResponseEntity.badRequest().build();

        // Criptografa a senha usando o BCrypt
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.password());

        // Cria um usuário usando os dados enviados pelo formulário
        User user = new User(dto.firstName(), dto.lastName(), dto.email(), senhaCriptografada, dto.role());

        // Salva o usuário
        this.userRepository.save(user);

        // Retorna o 200 OK com o usuário
        return ResponseEntity.ok().body(user);
    }

}
