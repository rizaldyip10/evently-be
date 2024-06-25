package com.pwdk.minpro_be.users.service;

import com.pwdk.minpro_be.users.dto.RegisterUserDto;
import com.pwdk.minpro_be.users.entity.User;

import java.util.List;

public interface UserService {
    User register(RegisterUserDto user);
    User findByEmail(String user);
    User findById(Long id);
    List<User> findAll();
    void deletedAt(Long id);
    User profile();
}
