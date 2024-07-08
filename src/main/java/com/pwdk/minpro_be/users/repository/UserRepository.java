package com.pwdk.minpro_be.users.repository;

import com.pwdk.minpro_be.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByReferralCode(String referralCode);

//    Optional<User> findByPhone(Integer phone);
}
