package com.pwdk.minpro_be.vouchers.repository;

import com.pwdk.minpro_be.vouchers.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
}
