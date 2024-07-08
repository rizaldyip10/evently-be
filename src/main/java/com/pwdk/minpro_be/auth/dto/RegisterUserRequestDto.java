package com.pwdk.minpro_be.auth.dto;

import com.pwdk.minpro_be.users.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RegisterUserRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Please select your role")
    private String role;

    private String referralCode;

    public User toEntity(){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
