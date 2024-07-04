package com.pwdk.minpro_be.userRole.service.Impl;

import com.pwdk.minpro_be.userRole.Entity.UserRole;
import com.pwdk.minpro_be.userRole.repository.UserRoleRepository;
import com.pwdk.minpro_be.userRole.service.UserRoleService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.roles.entity.Roles;
import com.pwdk.minpro_be.roles.repository.RoleRepository;
import com.pwdk.minpro_be.users.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public UserRoleImpl(UserRoleRepository userRoleRepository, RoleRepository roleRepository){
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserRole role(String userRole, User user) {
        Optional<Roles> role = roleRepository.findByName(userRole);
        if(role.isEmpty()){
            throw new ApplicationException("Role not found");
        }
        UserRole newUserRole = new UserRole();
        newUserRole.setUser(user);
        newUserRole.setRole(role.get());
        return userRoleRepository.save(newUserRole);
    }

    @Override
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public  UserRole findById(Long Id){
        return userRoleRepository.findByUserId(Id).orElseThrow(()-> new ApplicationException("User not found"));
    }



}
