package com.pwdk.minpro_be.trx.repository;

import com.pwdk.minpro_be.trx.entity.Trx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrxRepository extends JpaRepository<Trx, Long> {
    List<Trx> findByEventIdAndDeletedAtIsNull(Long eventId);
    List<Trx> findByUserIdAndDeletedAtIsNull(Long userId);
}
