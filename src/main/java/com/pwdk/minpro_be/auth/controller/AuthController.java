package com.pwdk.minpro_be.auth.controller;

import com.pwdk.minpro_be.auth.dto.LoginRequestDto;
import com.pwdk.minpro_be.auth.dto.LoginResponseDto;
import com.pwdk.minpro_be.auth.entity.UserAuth;
import com.pwdk.minpro_be.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto userlogin){
        log.info("User login request received for user: " + userlogin.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userlogin.getEmail(),userlogin.getPassword()));

        var ctx = SecurityContextHolder.getContext();
        ctx.setAuthentication(authentication);

        UserAuth userDetails = (UserAuth) authentication.getPrincipal();
        log.info("Token requested for user: " + userDetails.getUsername() + "with role:" + userDetails.getAuthorities().toArray()[0]);
        String token = authService.generatedToken(authentication);

        log.info(token);

        LoginResponseDto response = new LoginResponseDto();
        response.setMessage("User logged in successfully");
        response.setToken(token);

        Cookie cookie = new Cookie("sid", token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie.getName() + "=" + cookie.getValue() + "; Path=/; HttpOnly");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);

    }
}
