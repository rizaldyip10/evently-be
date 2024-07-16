package com.pwdk.minpro_be.auth.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;
import java.util.Objects;

public class Claims {

    public static Map<String, Object> getClaimsFromJwt(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaims();
    }
}
