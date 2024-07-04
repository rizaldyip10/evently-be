package com.pwdk.minpro_be.roles.controller;


import com.pwdk.minpro_be.roles.entity.Roles;
import com.pwdk.minpro_be.roles.repository.RoleRepository;
import com.pwdk.minpro_be.roles.service.RoleService;
import lombok.extern.java.Log;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@Validated
@Log
public class RoleController {

  private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<List<Roles>> findAll (){
        List<Roles> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }
}
