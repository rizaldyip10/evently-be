package com.pwdk.minpro_be.trx.repository;

import com.pwdk.minpro_be.trx.entity.TrxPromo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrxPromoRepository extends JpaRepository<TrxPromo, Long> {
}
