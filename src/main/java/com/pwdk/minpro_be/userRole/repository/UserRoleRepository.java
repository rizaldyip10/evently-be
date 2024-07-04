package com.pwdk.minpro_be.userRole.repository;

import com.pwdk.minpro_be.userRole.Entity.UserRole;
import com.pwdk.minpro_be.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUserId(Long id);

}
