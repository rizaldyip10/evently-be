package com.pwdk.minpro_be.userRole.service;

import com.pwdk.minpro_be.userRole.Entity.UserRole;
import com.pwdk.minpro_be.users.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRoleService {
    UserRole role (String userRole, User user);
    List<UserRole> findAll();
    UserRole findById(Long Id);
}
