package com.pwdk.minpro_be.point.repository;

import com.pwdk.minpro_be.point.entity.PointTrxType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointTrxTypeRepository extends JpaRepository<PointTrxType, Long> {
}
