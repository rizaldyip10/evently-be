package com.pwdk.minpro_be.roles.service.Impl;

import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.roles.entity.Roles;
import com.pwdk.minpro_be.roles.repository.RoleRepository;
import com.pwdk.minpro_be.roles.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RolesServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }


    @Override
    public Roles findByName(String name) {
        Optional<Roles> role = roleRepository.findByName(name);
        if(role.isEmpty()){
            throw new ApplicationException("Role not found");
        }
        return role.get();
    }

    @Override
    public List<Roles> findAll() {
        return roleRepository.findAll();
    }
}
