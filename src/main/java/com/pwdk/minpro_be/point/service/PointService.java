package com.pwdk.minpro_be.point.service;

import com.pwdk.minpro_be.point.entity.PointTrxType;
import com.pwdk.minpro_be.point.entity.UserPoint;

public interface PointService {
    String addUserPoint(Long id, Double pointAdded);
    void createPointTrx(UserPoint userPoint, PointTrxType pointTrxType, Double point);
}
