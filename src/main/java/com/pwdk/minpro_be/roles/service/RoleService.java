package com.pwdk.minpro_be.roles.service;

import com.pwdk.minpro_be.roles.entity.Roles;

import java.util.List;

public interface RoleService {
    Roles findByName (String name);
    List<Roles> findAll();

}
