package com.pwdk.minpro_be.auth.service.impl;

import com.pwdk.minpro_be.auth.repository.AuthRedisRepository;
import com.pwdk.minpro_be.auth.service.AuthService;
import com.pwdk.minpro_be.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthRedisRepository authRedisRepository;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtEncoder jwtEncoder, AuthRedisRepository authRedisRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.authRedisRepository = authRedisRepository;
    }

    public String generateToken(Authentication authentication){
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(""));

        var existingkey = authRedisRepository.getJwtKey(authentication.getName());
        if(existingkey != null){
            log.info("Token already exist for user" + authentication.getName());
            return existingkey;
        }

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("userId", userRepository.findByEmail(authentication.getName()).get().getId())
                .build();

        var jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        authRedisRepository.saveJwtKey(authentication.getName(), jwt);
        return jwt;

    }


    @Override
    public String generatedToken(Authentication authentication) {
        return "";
    }
}
