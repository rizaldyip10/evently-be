package com.pwdk.minpro_be.vouchers.repository;

import com.pwdk.minpro_be.vouchers.entity.EventVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventVoucherRepository extends JpaRepository<EventVoucher, Long> {
    @Query("SELECT ev FROM EventVoucher ev WHERE ev.event.id = :eventId AND ev.expiredAt > :currentTime")
    List<EventVoucher> findByEventIdAndNotExpired(@Param("eventId") Long eventId, @Param("currentTime") Instant currentTime);
    Optional<EventVoucher> findByEventIdAndVoucherId(Long eventId, Long voucherId);
}
