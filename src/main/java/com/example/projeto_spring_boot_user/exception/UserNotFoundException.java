package com.example.projeto_spring_boot_user.exception;

// Excessão personalizada para caso um usuário não seja encontrado
public class UserNotFoundException extends Exception{
    public UserNotFoundException(){
        super("ERRO!!! Usuário não encontrado.");
    }
}
