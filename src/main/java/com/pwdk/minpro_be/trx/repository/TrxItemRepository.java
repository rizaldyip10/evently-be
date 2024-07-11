package com.pwdk.minpro_be.trx.repository;

import com.pwdk.minpro_be.trx.entity.TrxItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrxItemRepository extends JpaRepository<TrxItem, Long> {
    Optional<TrxItem> findByTrxId(Long trxId);
}
