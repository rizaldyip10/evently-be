package com.pwdk.minpro_be.auth.controller;

import com.pwdk.minpro_be.auth.dto.*;
import com.pwdk.minpro_be.auth.entity.UserAuth;
import com.pwdk.minpro_be.auth.helpers.Claims;
import com.pwdk.minpro_be.auth.service.AuthService;
import com.pwdk.minpro_be.responses.Response;
import com.pwdk.minpro_be.users.service.UserService;
import jakarta.servlet.http.Cookie;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserService userService){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto userlogin){
        log.info("User login request received for user: " + userlogin.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userlogin.getEmail(),userlogin.getPassword()));
        var ctx = SecurityContextHolder.getContext();
        ctx.setAuthentication(authentication);

        UserAuth userDetails = (UserAuth) authentication.getPrincipal();
        log.info("Principal " + userDetails.getUsername());
        log.info("Token requested for user: " + userDetails.getUsername() + "with role:" + userDetails.getAuthorities().toArray()[0]);
        String token = authService.generateToken(authentication);

        LoginResponseDto response = new LoginResponseDto();
        response.setMessage("User logged in successfully");
        response.setToken(token);

        Cookie cookie = new Cookie("sid", token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie.getName() + "=" + cookie.getValue() + "; Path=/; HttpOnly");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequestDto registerUserRequestDto){
        userService.register(registerUserRequestDto);
        return Response.success("User registered successfully");
    }

    @GetMapping("/profile")
    public ResponseEntity<?>profile(){
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        var user = userService.findByEmail(email);
        var response = new ProfileResponseDto();
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setProfileImg(user.getProfileImg());
        response.setRole(user.getRoles().getFirst());

        return Response.success("User profile", response);
    }
}
