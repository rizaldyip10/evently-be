package com.pwdk.minpro_be.users.service.Impl;

import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.users.dto.RegisterUserDto;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.repository.UserRepository;
import com.pwdk.minpro_be.users.service.UserService;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterUserDto user) {
        User newUser = user.toEntity();
        var password = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(password);
        return userRepository.save(newUser);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ApplicationException("User not found"));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ApplicationException("User not found"));
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void deletedAt(Long id) {
    }

    @Override
    public User profile() {
        return null;
    }
}
