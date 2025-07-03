package com.example.projeto_spring_boot_user.domain;

import com.example.projeto_spring_boot_user.util.UserRole;
import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String nome;

    @Setter
    private String sobrenome;

    @Setter
    @Column(unique = true)
    private String email;

    @Setter
    private String senha;

    @Setter
    private LocalDate dataNascimento;

    @Setter
    private UserRole administrador = UserRole.USER;

    public User() {}

    public User(String nome, String sobrenome, String email, String senha, LocalDate dataNascimento) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
    }

    public User(String nome, String sobrenome, String email, String senha, LocalDate dataNascimento, UserRole administrador) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.administrador = administrador;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public UserRole getAdministrador() {
        return administrador;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.administrador == UserRole.ADMIN) {
            // Se o usuário for admin, retorna uma lista com ROLE_ADMIN e ROLE_USER
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        } else {
            // Se for um usuário comum, retorna apenas ROLE_USER
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getNome(), user.getNome()) && Objects.equals(getSobrenome(), user.getSobrenome()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getDataNascimento(), user.getDataNascimento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getSobrenome(), getEmail(), getDataNascimento());
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
