package com.pwdk.minpro_be.users.repository;

import com.pwdk.minpro_be.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

//    Optional<User> findByPhone(Integer phone);
}
