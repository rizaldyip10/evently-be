package com.pwdk.minpro_be.users.controller;

import com.pwdk.minpro_be.auth.helpers.Claims;
import com.pwdk.minpro_be.users.service.UserService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/referral-code")
    public ResponseEntity<?> generateReferralCode() {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(userService.generateReferralCode(email));
    }
}
