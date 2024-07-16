package com.pwdk.minpro_be.vouchers.service.impl;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.service.EventService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.service.UserService;
import com.pwdk.minpro_be.vouchers.dto.CreateVoucherRequestDto;
import com.pwdk.minpro_be.vouchers.dto.VoucherResponseDto;
import com.pwdk.minpro_be.vouchers.entity.EventVoucher;
import com.pwdk.minpro_be.vouchers.entity.UserVoucher;
import com.pwdk.minpro_be.vouchers.entity.Voucher;
import com.pwdk.minpro_be.vouchers.repository.EventVoucherRepository;
import com.pwdk.minpro_be.vouchers.repository.UserVoucherRepository;
import com.pwdk.minpro_be.vouchers.repository.VoucherRepository;
import com.pwdk.minpro_be.vouchers.service.VoucherService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return userVoucherRepository.findByUserIdAndIsValidTrue(user.getId());
    }

    @Override
    public List<EventVoucher> getEventVouchers(String eventSlug) {
        Event event = eventService.findBySlug(eventSlug);
        return eventVoucherRepository.findByEventIdAndNotExpired(event.getId(), Instant.now());
    }

    @Override
    @Transactional
    public EventVoucher createEventVoucher(
            CreateVoucherRequestDto createVoucherRequestDto,
            String eventSlug,
            String userEmail
    ) {
        var organizer = userService.findByEmail(userEmail);
        var event = eventService.findBySlug(eventSlug);

//        var isEventAdmin

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
    public void addUserVoucher(User user, Voucher voucher) {
        var newUserVoucher = new UserVoucher();
        newUserVoucher.setUser(user);
        newUserVoucher.setVoucher(voucher);
        newUserVoucher.setIsValid(true);
        userVoucherRepository.save(newUserVoucher);
    }

    @Override
    public Voucher getVoucherById(Long id) {
        Optional<Voucher> voucher = voucherRepository.findById(id);
        if (voucher.isEmpty()) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Voucher not found");
        }

        return voucher.get();
    }

    @Override
    public Voucher getVoucherByName(String voucherName) {
        return voucherRepository.findByNameIgnoreCase(voucherName)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Voucher not found"));
    }

    @Override
    public Optional<EventVoucher> getByEventIdAndVoucherId(Long eventId, Long voucherId) {
        return eventVoucherRepository.findByEventIdAndVoucherId(eventId, voucherId);
    }

    @Override
    public Optional<UserVoucher> getByUserIdAndVoucherId(Long userId, Long voucherId) {
        return userVoucherRepository.findByUserIdAndVoucherId(userId, voucherId);
    }

    @Override
    public Page<VoucherResponseDto> getActiveTrxVoucher(String eventSlug, String userEmail, Pageable pageable) {
        List<Voucher> activeTrxVoucher = new ArrayList<>();

        List<EventVoucher> eventVouchers = getEventVouchers(eventSlug);
        for(EventVoucher eventVoucher : eventVouchers) {
            Voucher voucher = eventVoucher.getVoucher();
            activeTrxVoucher.add(voucher);
        }

        List<UserVoucher> userVouchers = getUserVouchers(userEmail);
        for (UserVoucher userVoucher : userVouchers) {
            Voucher voucher = userVoucher.getVoucher();
            activeTrxVoucher.add(voucher);
        }

        if (pageable.getSort().isSorted()) {
            activeTrxVoucher.sort((v1, v2) -> {
                for (Sort.Order order : pageable.getSort()) {
                    int comparison = 0;
                    if (order.getProperty().equals("id")) {
                        comparison = v1.getId().compareTo(v2.getId());
                    }
                    if (comparison != 0) {
                        return order.isAscending() ? comparison : -comparison;
                    }
                }
                return 0;
            });
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), activeTrxVoucher.size());
        List<VoucherResponseDto> pageContent = activeTrxVoucher.subList(start, end)
                .stream().map(Voucher::toDto).collect(Collectors.toList());

        return new PageImpl<>(pageContent, pageable, activeTrxVoucher.size());
    }
}
