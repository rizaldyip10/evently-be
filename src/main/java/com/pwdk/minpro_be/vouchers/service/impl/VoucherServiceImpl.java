package com.pwdk.minpro_be.vouchers.service.impl;

import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.vouchers.entity.EventVoucher;
import com.pwdk.minpro_be.vouchers.entity.UserVoucher;
import com.pwdk.minpro_be.vouchers.entity.Voucher;
import com.pwdk.minpro_be.vouchers.repository.EventVoucherRepository;
import com.pwdk.minpro_be.vouchers.repository.UserVoucherRepository;
import com.pwdk.minpro_be.vouchers.repository.VoucherRepository;
import com.pwdk.minpro_be.vouchers.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final EventVoucherRepository eventVoucherRepository;
    private final UserVoucherRepository userVoucherRepository;


    public VoucherServiceImpl(VoucherRepository voucherRepository, EventVoucherRepository eventVoucherRepository, UserVoucherRepository userVoucherRepository) {
        this.voucherRepository = voucherRepository;
        this.eventVoucherRepository = eventVoucherRepository;
        this.userVoucherRepository = userVoucherRepository;
    }

    @Override
    public List<UserVoucher> getUserVouchers(Long userId) {
        return userVoucherRepository.findByUserId(userId);
    }

    @Override
    public List<EventVoucher> getEventVouchers(Long eventId) {
        return eventVoucherRepository.findByEventId(eventId);
    }

    @Override
    public EventVoucher createEventVoucher() {
        return null;
    }

    @Override
    public UserVoucher addUserVoucher(User user, Voucher voucher) {
        var newUserVoucher = new UserVoucher();
        newUserVoucher.setUser(user);
        newUserVoucher.setVoucher(voucher);
        newUserVoucher.setIsValid(true);
        return newUserVoucher;
    }

    @Override
    public Voucher getVoucherById(Long id) {
        Optional<Voucher> voucher = voucherRepository.findById(id);
        if (voucher.isEmpty()) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Voucher not found");
        }

        return voucher.get();
    }
}
