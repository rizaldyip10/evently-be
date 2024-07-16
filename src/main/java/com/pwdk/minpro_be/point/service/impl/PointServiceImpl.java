package com.pwdk.minpro_be.point.service.impl;

import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.exception.DataNotFoundException;
import com.pwdk.minpro_be.point.entity.PointTrx;
import com.pwdk.minpro_be.point.entity.PointTrxType;
import com.pwdk.minpro_be.point.entity.UserPoint;
import com.pwdk.minpro_be.point.repository.PointTrxRepository;
import com.pwdk.minpro_be.point.repository.PointTrxTypeRepository;
import com.pwdk.minpro_be.point.repository.UserPointRepository;
import com.pwdk.minpro_be.point.service.PointService;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class PointServiceImpl implements PointService {
    private final UserPointRepository userPointRepository;
    private final PointTrxRepository pointTrxRepository;
    private final PointTrxTypeRepository pointTrxTypeRepository;
    private final UserService userService;

    public PointServiceImpl(UserPointRepository userPointRepository,
                            PointTrxRepository pointTrxRepository,
                            PointTrxTypeRepository pointTrxTypeRepository,
                            @Lazy UserService userService)
    {
        this.userPointRepository = userPointRepository;
        this.pointTrxRepository = pointTrxRepository;
        this.pointTrxTypeRepository = pointTrxTypeRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public String addUserPoint(Long id, Double pointAdded) {
        User referredUser = userService.findById(id);
        Optional<PointTrxType> income = pointTrxTypeRepository.findById(1L);

        if (income.isEmpty()) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Point trx type not found");
        }

        Optional<UserPoint> isUserPointExist = userPointRepository.findByUserId(id);

        if (isUserPointExist.isEmpty()) {
            var newUserPoint = new UserPoint();
            newUserPoint.setUser(referredUser);
            newUserPoint.setTotalPoint(pointAdded);
            newUserPoint.setExpiredAt(Instant.now().plus(3, ChronoUnit.MONTHS));
            var newAddedUserPoint = userPointRepository.save(newUserPoint);

            createPointTrx(newAddedUserPoint, income.get(), pointAdded);

            return "New user point added";
        }

        Double currPoint = isUserPointExist.get().getTotalPoint();
        Instant currExpiredAt = isUserPointExist.get().getExpiredAt();
        isUserPointExist.get().setTotalPoint(currPoint + pointAdded);
        isUserPointExist.get().setExpiredAt(currExpiredAt.plus(3, ChronoUnit.MONTHS));
        var updatedUserPoint = userPointRepository.save(isUserPointExist.get());

        createPointTrx(updatedUserPoint, income.get(), pointAdded);

        return "User point added";
    }

    @Override
    public void createPointTrx(UserPoint userPoint, PointTrxType pointTrxType, Double point) {
        PointTrx newTrx = new PointTrx();
        newTrx.setPointTrxType(pointTrxType);
        newTrx.setUserPoint(userPoint);
        newTrx.setPoint(point);

        pointTrxRepository.save(newTrx);
    }

    @Override
    public UserPoint getUserPoint(Long id) {
        return userPointRepository.findByUserId(id)
                .orElseThrow(() -> new DataNotFoundException("User don't have points"));
    }

    @Override
    public PointTrxType getPointTrxType(Long id) {
        return pointTrxTypeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Point trx type not found"));
    }


}
