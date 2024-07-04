package com.pwdk.minpro_be.vouchers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventVoucherRepository extends JpaRepository<EventVoucherRepository, Long> {
}
