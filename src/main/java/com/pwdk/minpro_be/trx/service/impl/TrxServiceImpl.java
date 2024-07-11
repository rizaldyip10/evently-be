package com.pwdk.minpro_be.trx.service.impl;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.service.EventService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.ticket.Service.TicketService;
import com.pwdk.minpro_be.ticket.entity.Ticket;
import com.pwdk.minpro_be.trx.dto.PaymentRequestDto;
import com.pwdk.minpro_be.trx.dto.TicketBoughtListDto;
import com.pwdk.minpro_be.trx.dto.TicketTrxRequestDto;
import com.pwdk.minpro_be.trx.dto.VoucherPaymentDto;
import com.pwdk.minpro_be.trx.entity.PaymentStatus;
import com.pwdk.minpro_be.trx.entity.Trx;
import com.pwdk.minpro_be.trx.entity.TrxItem;
import com.pwdk.minpro_be.trx.entity.TrxPromo;
import com.pwdk.minpro_be.trx.repository.*;
import com.pwdk.minpro_be.trx.service.TrxService;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.service.UserService;
import com.pwdk.minpro_be.vouchers.entity.EventVoucher;
import com.pwdk.minpro_be.vouchers.entity.UserVoucher;
import com.pwdk.minpro_be.vouchers.entity.Voucher;
import com.pwdk.minpro_be.vouchers.service.VoucherService;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log
public class TrxServiceImpl implements TrxService {
    private final TrxRepository trxRepository;
    private final TrxItemRepository trxItemRepository;
    private final TrxPromoRepository trxPromoRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final UserService userService;
    private final EventService eventService;
    private final TicketService ticketService;
    private final VoucherService voucherService;

    public TrxServiceImpl(TrxRepository trxRepository,
                          TrxItemRepository trxItemRepository,
                          TrxPromoRepository trxPromoRepository,
                          PaymentMethodRepository paymentMethodRepository,
                          PaymentStatusRepository paymentStatusRepository,
                          @Lazy UserService userService,
                          @Lazy EventService eventService,
                          @Lazy TicketService ticketService,
                          @Lazy VoucherService voucherService) {
        this.trxRepository = trxRepository;
        this.trxItemRepository = trxItemRepository;
        this.trxPromoRepository = trxPromoRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentStatusRepository = paymentStatusRepository;
        this.userService = userService;
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.voucherService = voucherService;
    }


    @Override
    public Trx createUserTrx(String userEmail, String eventSlug) {
        User user = userService.findByEmail(userEmail);
        Event event = eventService.findBySlug(eventSlug);
        PaymentStatus paymentStatus = paymentStatusRepository.findById(2L)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Payment Status not found"));

        Instant now = Instant.now();
        ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = zonedDateTime.format(formatter);

        String invoiceNumber = "INV/" + formattedDate + "/" + event.getSlug() + user.getName().replace(" ", "-");

        var newTrx = new Trx();
        newTrx.setUser(user);
        newTrx.setEvent(event);
        newTrx.setInvoiceNumber(invoiceNumber);
        newTrx.setPaymentStatus(paymentStatus);
        return trxRepository.save(newTrx);
    }

    @Transactional
    @Override
    public Trx addingTrxItem(TicketTrxRequestDto ticketTrxRequestDto,
                             String userEmail,
                             String eventSlug,
                             Long trxId)
    {
        User user = userService.findByEmail(userEmail);
        Event event = eventService.findBySlug(eventSlug);
        Optional<Trx> trx = trxRepository.findById(trxId);

        if (trx.isEmpty()) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Transaction not found");
        }

        if (!Objects.equals(trx.get().getUser().getId(), user.getId())) {
            throw new ApplicationException(HttpStatus.CONFLICT, "User not match. You cannot access this transaction");
        }

        if (!Objects.equals(trx.get().getEvent().getId(), event.getId())) {
            throw new ApplicationException(HttpStatus.CONFLICT, "Event not match. You cannot access this transaction");
        }

        double totalPrice = 0;

        if (!ticketTrxRequestDto.getTicketList().isEmpty()) {
            for (TicketBoughtListDto ticketDto : ticketTrxRequestDto.getTicketList()) {
                Optional<Ticket> ticket = ticketService.getTicketById(ticketDto.getId());
                if (ticket.isEmpty()) {
                    throw new ApplicationException(HttpStatus.NOT_FOUND, "Ticket not found with id: " + ticketDto.getId());
                }

                var newTrxItem = new TrxItem();
                newTrxItem.setTrx(trx.get());
                newTrxItem.setTicket(ticket.get());
                newTrxItem.setAmount(ticketDto.getAmount());
                trxItemRepository.save(newTrxItem);

                var updatedTicketQuota = ticket.get().getQuota() - ticketDto.getAmount();
                ticketService.updateTicketQuota(ticket.get().getId(), updatedTicketQuota);

                totalPrice = totalPrice + (ticketDto.getAmount() * ticketDto.getPrice());
            }
        }

        trx.get().setTotalPrice(totalPrice);

        return trxRepository.save(trx.get());
    }

    @Transactional
    @Override
    public Trx addingPaymentMethod(PaymentRequestDto paymentRequestDto,
                                   String userEmail,
                                   String eventSlug,
                                   Long trxId)
    {
        User user = userService.findByEmail(userEmail);
        Event event = eventService.findBySlug(eventSlug);
        Optional<Trx> trx = trxRepository.findById(trxId);
        Instant now = Instant.now();
        List<Voucher> trxVoucher = new ArrayList<>();

        if (trx.isEmpty()) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Transaction not found");
        }

        if (!Objects.equals(trx.get().getUser().getId(), user.getId())) {
            throw new ApplicationException(HttpStatus.CONFLICT, "User not match. You cannot access this transaction");
        }

        if (!Objects.equals(trx.get().getEvent().getId(), event.getId())) {
            throw new ApplicationException(HttpStatus.CONFLICT, "Event not match. You cannot access this transaction");
        }

        double totalDiscountedPrice = 0;
        double endPrice = trx.get().getTotalPrice();

        if (!paymentRequestDto.getVouchers().isEmpty()) {
            for (VoucherPaymentDto voucherDto : paymentRequestDto.getVouchers()) {
                Voucher voucher = voucherService.getVoucherByName(voucherDto.getName());
                Optional<EventVoucher> eventVoucher = voucherService.getByEventIdAndVoucherId(event.getId(), voucher.getId());
                if (eventVoucher.isPresent()) {
                    if (now.isAfter(eventVoucher.get().getExpiredAt())) {
                        throw new ApplicationException(HttpStatus.BAD_REQUEST, "Voucher is expired");
                    }

                    var newTrxPromo = new TrxPromo();
                    newTrxPromo.setVoucher(voucher);
                    newTrxPromo.setTrx(trx.get());
                    trxPromoRepository.save(newTrxPromo);
                    trxVoucher.add(voucher);
                }

                Optional<UserVoucher> userVoucher = voucherService.getByUserIdAndVoucherId(user.getId(), voucher.getId());
                if (userVoucher.isPresent()) {
                    if (!userVoucher.get().getIsValid()) {
                        throw new ApplicationException(HttpStatus.BAD_REQUEST, "Your voucher is not valid");
                    }
                    var newTrxPromo = new TrxPromo();
                    newTrxPromo.setVoucher(voucher);
                    newTrxPromo.setTrx(trx.get());
                    trxPromoRepository.save(newTrxPromo);
                    trxVoucher.add(voucher);
                }

                totalDiscountedPrice = totalDiscountedPrice + (endPrice * voucher.getDiscount().doubleValue());
                endPrice = endPrice - totalDiscountedPrice;
            }
        }

        var paymentMethod = paymentMethodRepository.findByName(paymentRequestDto.getPaymentMethod())
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Payment Method not found"));
        PaymentStatus paymentStatus = paymentStatusRepository.findById(1L)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Payment Status not found"));

        trx.get().setTotalDiscount(totalDiscountedPrice);
        trx.get().setFinalPrice(endPrice);
        trx.get().setPaymentMethod(paymentMethod);
        trx.get().setPaymentStatus(paymentStatus);
        trx.get().setVouchers(trxVoucher);
        return trxRepository.save(trx.get());
    }

    @Override
    public Trx getTrxDetail(Long trxId) {
        return trxRepository.findById(trxId)
                .orElseThrow(() -> new ApplicationException(HttpStatus.BAD_REQUEST, "Transaction not found"));
    }

    @Override
    public List<Trx> getEventTrx(String eventSlug) {
        Event event = eventService.findBySlug(eventSlug);
        return trxRepository.findByEventIdAndDeletedAtIsNull(event.getId());
    }

    @Override
    public List<Trx> getUserTrx(String email) {
        User user = userService.findByEmail(email);
        return trxRepository.findByUserIdAndDeletedAtIsNull(user.getId());
    }

    @Override
    public String deleteUserTrx(String email, Long trxId) {
        User user = userService.findByEmail(email);
        Trx trx = trxRepository.findById(trxId)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Transaction not found"));

        if (!Objects.equals(trx.getUser().getId(), user.getId())) {
            throw new ApplicationException(HttpStatus.CONFLICT, "User not match");
        }

        if (!trx.getTrxItems().isEmpty()) {
            for (TrxItem trxItem : trx.getTrxItems()) {
                Ticket ticket = ticketService.getTicketById(trxItem.getTicket().getId())
                        .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Ticket not found"));

                var updatedTicketQuota = ticket.getQuota() + trxItem.getAmount();
                ticketService.updateTicketQuota(ticket.getId(), updatedTicketQuota);
            }
        }

        trx.setDeletedAt(Instant.now().atZone(ZoneId.systemDefault()).toInstant());
        trxRepository.save(trx);
        return "Transaction deleted";
    }
}
