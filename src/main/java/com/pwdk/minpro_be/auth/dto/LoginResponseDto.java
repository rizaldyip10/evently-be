package com.pwdk.minpro_be.auth.dto;

import com.pwdk.minpro_be.users.dto.UserDto;
import lombok.Data;

@Data
public class LoginResponseDto {
    private String message;
    private String token;
    private UserDto user;

}
