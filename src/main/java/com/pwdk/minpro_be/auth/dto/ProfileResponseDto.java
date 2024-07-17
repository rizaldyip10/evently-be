package com.pwdk.minpro_be.auth.dto;

import com.pwdk.minpro_be.point.dto.PointResponseDto;
import com.pwdk.minpro_be.roles.entity.Roles;
import lombok.Data;

@Data
public class ProfileResponseDto {
    private String name;
    private String email;
    private String profileImg;
    private Roles role;
    private PointResponseDto points;
}
