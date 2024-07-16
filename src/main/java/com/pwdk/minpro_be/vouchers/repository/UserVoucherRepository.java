package com.pwdk.minpro_be.vouchers.repository;

import com.pwdk.minpro_be.vouchers.entity.UserVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserVoucherRepository extends JpaRepository<UserVoucher, Long> {
    List<UserVoucher> findByUserIdAndIsValidTrue(Long userId);
    Optional<UserVoucher> findByUserIdAndVoucherId(Long userId, Long voucherId);
}
