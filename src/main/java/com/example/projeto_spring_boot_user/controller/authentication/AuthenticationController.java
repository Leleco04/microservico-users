package com.example.projeto_spring_boot_user.controller.authentication;

import com.example.projeto_spring_boot_user.domain.User;
import com.example.projeto_spring_boot_user.dto.AuthenticationDTO;
import com.example.projeto_spring_boot_user.dto.LoginResponseDTO;
import com.example.projeto_spring_boot_user.dto.RegisterDTO;
import com.example.projeto_spring_boot_user.dto.ResponseDTO;
import com.example.projeto_spring_boot_user.service.TokenService;
import com.example.projeto_spring_boot_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

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
        // Cria o usuário de resposta
        ResponseDTO responseDTO = userService.create(dto);

        if(responseDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        // Retorna o 200 OK com o usuário
        return ResponseEntity.ok().body(responseDTO);
    }

}
