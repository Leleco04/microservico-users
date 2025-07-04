package com.example.projeto_spring_boot_user.security;

import com.example.projeto_spring_boot_user.repository.UserRepository;
import com.example.projeto_spring_boot_user.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recupera o token
        var token = this.recoverToken(request);

        // Se o token não for null
        if(token != null) {
            // Válida o token e extrai o e-mail do usuário
            var email = tokenService.validateToken(token);
            // Armazena os detalhes do usuário
            UserDetails userDetails = userRepository.findByEmail(email);

            if(userDetails != null) {
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    // Método para recuperar o token
    private String recoverToken(HttpServletRequest request) {
        // Busca e armazena o valor da header no qual o token é passado (Authorization)
        var authHeader = request.getHeader("Authorization");

        // Se não tiver nenhum token, retorna null
        if(authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}