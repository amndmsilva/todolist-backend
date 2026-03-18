package com.amandasantos.todolist.repository;

import com.amandasantos.todolist.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail (String email);
}
