package com.pwdk.minpro_be.vouchers.repository;

import com.pwdk.minpro_be.vouchers.entity.EventVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventVoucherRepository extends JpaRepository<EventVoucher, Long> {
    List<EventVoucher> findByEventId(Long eventId);
    Optional<EventVoucher> findByEventIdAndVoucherId(Long eventId, Long voucherId);
}
