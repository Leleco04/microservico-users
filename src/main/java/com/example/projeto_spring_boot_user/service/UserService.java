package com.example.projeto_spring_boot_user.service;

import com.example.projeto_spring_boot_user.exception.UserNotFoundException;
import com.example.projeto_spring_boot_user.model.User;
import com.example.projeto_spring_boot_user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private int c = 0;

    @Autowired
    private UserRepository repo;

    public List<User> findAll() {
        if(c == 0) {
            for(long i = 0; i < 10; i++) {
                User u = new User("Nome " + i, "Sobrenome " + i, "email" + i + "@gmail.com", LocalDate.now());
                repo.save(u);
            }
            c = 1;
        }
        return repo.findAll();
    }

    public User findById(Long id) throws UserNotFoundException {
        return repo.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public void deleteById(Long id) throws UserNotFoundException {
        repo.deleteById(id);
    }

    public User create(User user) {
        return repo.save(user);
    }

    public User delete(Long id) throws UserNotFoundException {
        User u = findById(id);
        repo.delete(u);
        return u;
    }
 }
