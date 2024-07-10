package com.pwdk.minpro_be.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import com.pwdk.minpro_be.auth.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.http.Cookie;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Log
public class SecurityConfig {
    private final RsaKeyConfigProperties rsaKeyConfigProperties;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(RsaKeyConfigProperties rsaKeyConfigProperties, UserDetailsServiceImpl userDetailsService){
        this.rsaKeyConfigProperties = rsaKeyConfigProperties;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager (){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/error/**").permitAll();
                    auth.requestMatchers("/api/v1/auth/login").permitAll();
                    auth.requestMatchers("/api/v1/auth/register").permitAll();
                    auth.requestMatchers("/api/v1/event/create").permitAll();
                    auth.requestMatchers("api/v1/event").permitAll();
                    auth.requestMatchers("/api/v1/ticket").permitAll();
                    auth.requestMatchers("/api/v1/role").permitAll();
                    auth.requestMatchers("/api/v1/categories/**").permitAll();
                    auth.requestMatchers("/api/v1/role/user").permitAll();
                    auth.requestMatchers("/api/v1/event-organization").permitAll();
                    auth.requestMatchers("/api/v1/users/referral-code").hasAuthority("SCOPE_ROLE_USER");
                    auth.requestMatchers(HttpMethod.POST,"/api/v1/voucher/{eventSlug}").hasAuthority("SCOPE_ROLE_ORGANIZER");
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/voucher/{eventSlug}").permitAll();
                    auth.requestMatchers("/api/v1/voucher/user-voucher").hasAuthority("SCOPE_ROLE_USER");
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/review/{eventSlug}").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/api/v1/review/{eventSlug}").hasAuthority("SCOPE_ROLE_USER");
                    auth.requestMatchers(HttpMethod.PUT, "/api/v1/review/user-review/{reviewId}").hasAuthority("SCOPE_USER_ROLE");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/review/user-review/{reviewId}").hasAnyAuthority("SCOPE_ROLE_USER", "SCOPE_ROLE_ORGANIZER");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((oauth2) -> {
                    oauth2.jwt((jwt) -> jwt.decoder(jwtDecoder()));
                    oauth2.bearerTokenResolver((request) -> {
                        // This is where you can get the token from the cookie
                        // By default, it gets the token from the Authorization header
                        // Read more here https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/web/BearerTokenResolver.html
                        Cookie[] cookies = request.getCookies();
                        if (cookies != null) {
                            for (Cookie cookie : cookies) {
                                if ("sid".equals(cookie.getName())) {
                                    return cookie.getValue();
                                }
                            }
                        }
                        return null;
                    });
                })
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeyConfigProperties.publicKey()).privateKey(rsaKeyConfigProperties.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

}
