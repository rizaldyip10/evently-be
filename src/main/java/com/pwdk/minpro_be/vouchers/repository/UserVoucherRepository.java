package com.pwdk.minpro_be.vouchers.repository;

import com.pwdk.minpro_be.vouchers.entity.UserVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVoucherRepository extends JpaRepository<UserVoucher, Long> {
}
