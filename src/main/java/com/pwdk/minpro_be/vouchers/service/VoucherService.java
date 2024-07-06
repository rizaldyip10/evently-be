package com.pwdk.minpro_be.vouchers.service;

import com.pwdk.minpro_be.vouchers.entity.EventVoucher;
import com.pwdk.minpro_be.vouchers.entity.UserVoucher;

import java.util.List;

public interface VoucherService {
    List<UserVoucher> getUserVouchers();
    List<EventVoucher> getEventVouchers();
    EventVoucher createEventVoucher();
    UserVoucher addUserVoucher();
}
