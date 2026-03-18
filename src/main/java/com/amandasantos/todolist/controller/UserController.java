package com.amandasantos.todolist.controller;

import com.amandasantos.todolist.entity.UserEntity;
import com.amandasantos.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserEntity userEntity) {
        var user = this.userRepository.findByEmail(userEntity.getEmail());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userEntity.getPassword().toCharArray());

        userEntity.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userEntity);
        return ResponseEntity.ok().body(userCreated);
    }
}
