package com.pwdk.minpro_be.users.controller;

import com.pwdk.minpro_be.responses.Response;
import com.pwdk.minpro_be.users.dto.RegisterUserDto;
import com.pwdk.minpro_be.users.service.UserService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Log
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto){
        return Response.success("User registered successfully", userService.register(registerUserDto));
    }

    @GetMapping("/profile")
    public ResponseEntity<?>profile(@RequestBody String email){
        return Response.success("User profile", userService.findByEmail(email));
    }
}
