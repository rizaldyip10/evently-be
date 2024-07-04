package com.pwdk.minpro_be.users.service.Impl;

import com.pwdk.minpro_be.userRole.service.UserRoleService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.users.dto.RegisterUserDto;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.repository.UserRepository;
import com.pwdk.minpro_be.users.service.UserService;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleService userRoleService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
    }

    @Override
    public User register(RegisterUserDto user) {
//        User newUser = user.toEntity();
        Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist.isPresent()) {
            throw new ApplicationException("User already exist");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        var password = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(password);

        var userRegistered  =  userRepository.save(newUser);
        userRoleService.role(user.getRole(), userRegistered);
        return userRegistered;
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
