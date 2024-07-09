package com.pwdk.minpro_be.vouchers.service.impl;

import com.pwdk.minpro_be.event.service.EventService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.service.UserService;
import com.pwdk.minpro_be.vouchers.dto.CreateVoucherRequestDto;
import com.pwdk.minpro_be.vouchers.entity.EventVoucher;
import com.pwdk.minpro_be.vouchers.entity.UserVoucher;
import com.pwdk.minpro_be.vouchers.entity.Voucher;
import com.pwdk.minpro_be.vouchers.repository.EventVoucherRepository;
import com.pwdk.minpro_be.vouchers.repository.UserVoucherRepository;
import com.pwdk.minpro_be.vouchers.repository.VoucherRepository;
import com.pwdk.minpro_be.vouchers.service.VoucherService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final EventVoucherRepository eventVoucherRepository;
    private final UserVoucherRepository userVoucherRepository;
    private final EventService eventService;
    private final UserService userService;


    public VoucherServiceImpl(
            VoucherRepository voucherRepository,
            EventVoucherRepository eventVoucherRepository,
            UserVoucherRepository userVoucherRepository,
            @Lazy EventService eventService,
            @Lazy UserService userService)
    {
        this.voucherRepository = voucherRepository;
        this.eventVoucherRepository = eventVoucherRepository;
        this.userVoucherRepository = userVoucherRepository;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Override
    public List<UserVoucher> getUserVouchers(String email) {
        User user = userService.findByEmail(email);
        return userVoucherRepository.findByUserId(user.getId());
    }

    @Override
    public List<EventVoucher> getEventVouchers(Long eventId) {
        return eventVoucherRepository.findByEventId(eventId);
    }

    @Override
    public EventVoucher createEventVoucher(
            CreateVoucherRequestDto createVoucherRequestDto,
            String eventSlug
    ) {
        var event = eventService.findBySlug(eventSlug);

        Voucher newVoucher = new Voucher();
        EventVoucher newEventVoucher = new EventVoucher();

        newVoucher.setName(createVoucherRequestDto.getName());
        newVoucher.setDiscount(createVoucherRequestDto.getDiscount());
        newEventVoucher.setEvent(event);
        newEventVoucher.setExpiredAt(createVoucherRequestDto.getExpiredDate().toInstant());

        voucherRepository.save(newVoucher);
        return eventVoucherRepository.save(newEventVoucher);
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
