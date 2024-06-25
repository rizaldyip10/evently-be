package com.pwdk.minpro_be.auth.service;

import org.springframework.security.core.Authentication;


public interface AuthService {
    String generatedToken(Authentication authentication);

}
