package com.example.projeto_spring_boot_user.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.projeto_spring_boot_user.repository.UserRepository;
import com.example.projeto_spring_boot_user.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

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
        if (token != null) {
            // Valida o token e extrai o ID do usuário (o subject)
            var userId = tokenService.validateToken(token);

            if (userId != null && !userId.isEmpty()) {
                // Decodifica o token para pegar a role diretamente da claim
                var role = JWT.decode(token).getClaim("role").asString();

                // Cria a autoridade (role) para o Spring Security
                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

                // Cria o objeto de autenticação.
                // O "principal" (o identificador principal) agora é o ID do usuário, não o objeto UserDetails.
                var authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);

                // Define a autenticação no contexto de segurança para esta requisição
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