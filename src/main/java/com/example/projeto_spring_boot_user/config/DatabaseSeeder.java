package com.example.projeto_spring_boot_user.config;

import com.example.projeto_spring_boot_user.domain.User;
import com.example.projeto_spring_boot_user.dto.RegisterDTO;
import com.example.projeto_spring_boot_user.service.UserService;
import com.example.projeto_spring_boot_user.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserService service;

    Pageable pageable;

    @Override
    public void run(String... args) throws Exception {
        Pageable firstPageWithOneElement = PageRequest.of(0, 1);

        if(service.findAll(firstPageWithOneElement).isEmpty()) {
            populateUsers();
        }
    }

    private void populateUsers() throws Exception {
        service.create(new RegisterDTO("Lumon", "Administrador", "lumon@gmail.com", "lumon", UserRole.ADMIN));
        service.create(new RegisterDTO("Leandro", "Hideki", "leandro@gmail.com", "leandro", UserRole.USER));
        service.create(new RegisterDTO("João", "Deliberador", "joao@gmail.com", "joao", UserRole.USER));
        service.create(new RegisterDTO("Graciele", "Miki", "graciele@gmail.com", "graciele", UserRole.USER));
        service.create(new RegisterDTO("Pedro", "Liboni", "pedro@gmail.com", "pedro", UserRole.USER));
        service.create(new RegisterDTO("Eduardo", "Czigler", "eduardo@gmail.com", "eduardo", UserRole.USER));
    }

}
