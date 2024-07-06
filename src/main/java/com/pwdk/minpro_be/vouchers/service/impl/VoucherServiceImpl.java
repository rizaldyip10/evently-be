package com.pwdk.minpro_be.vouchers.service.impl;

import com.pwdk.minpro_be.vouchers.entity.EventVoucher;
import com.pwdk.minpro_be.vouchers.entity.UserVoucher;
import com.pwdk.minpro_be.vouchers.repository.EventVoucherRepository;
import com.pwdk.minpro_be.vouchers.repository.UserVoucherRepository;
import com.pwdk.minpro_be.vouchers.repository.VoucherRepository;
import com.pwdk.minpro_be.vouchers.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<UserVoucher> getUserVouchers() {
        return null;
    }

    @Override
    public List<EventVoucher> getEventVouchers() {
        return null;
    }

    @Override
    public EventVoucher createEventVoucher() {
        return null;
    }

    @Override
    public UserVoucher addUserVoucher() {
        return null;
    }
}
