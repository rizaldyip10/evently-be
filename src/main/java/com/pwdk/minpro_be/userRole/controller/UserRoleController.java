package com.pwdk.minpro_be.userRole.controller;

import com.pwdk.minpro_be.responses.Response;
import com.pwdk.minpro_be.userRole.Entity.UserRole;
import com.pwdk.minpro_be.userRole.service.UserRoleService;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.repository.UserRepository;
import com.pwdk.minpro_be.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-role")
public class UserRoleController {

    private final UserRoleService userRoleService;
    private final UserService userService;

    public UserRoleController(UserRoleService userRoleService, UserService userService){
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> findAll(){
        return Response.success("User role" , userRoleService.findAll());
    }

    @GetMapping
    public ResponseEntity<?> findById(){
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        String requestEmail = auth.getName();
        User user = userService.findByEmail(requestEmail);
//        UserRole userId = userRoleService.findById(user.getId());
        return Response.success("User Role", userRoleService.findById(user.getId()));
    }


}
