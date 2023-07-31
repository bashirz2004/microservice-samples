package com.zamani.configurations.security;

import com.zamani.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<MyUser,UUID> {
    MyUser findByUsernameEquals(String username);
}
