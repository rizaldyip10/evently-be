package com.pwdk.minpro_be.users.service;

import com.pwdk.minpro_be.auth.dto.RegisterUserRequestDto;
import com.pwdk.minpro_be.users.entity.User;

import java.util.List;

public interface UserService {
    User register(RegisterUserRequestDto user);
    User findByEmail(String user);
    User findById(Long id);
    List<User> findAll();
    void deletedAt(Long id);
    User profile();
}
