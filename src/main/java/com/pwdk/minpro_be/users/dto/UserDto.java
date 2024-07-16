package com.pwdk.minpro_be.users.dto;

import com.pwdk.minpro_be.point.dto.PointResponseDto;
import com.pwdk.minpro_be.roles.entity.Roles;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String name;
    private String email;
    private String profileImg;
    private List<Roles> roles;
    private PointResponseDto points;
}
