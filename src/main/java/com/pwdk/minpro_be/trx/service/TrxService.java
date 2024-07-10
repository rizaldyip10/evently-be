package com.pwdk.minpro_be.trx.service;

import com.pwdk.minpro_be.trx.dto.PaymentRequestDto;
import com.pwdk.minpro_be.trx.dto.TicketTrxRequestDto;
import com.pwdk.minpro_be.trx.entity.Trx;

import java.util.List;

public interface TrxService {
    Trx createUserTrx(String userEmail, String eventSlug);
    Trx addingTrxItem(TicketTrxRequestDto ticketTrxRequestDto, String userEmail, String eventSlug, Long trxId);
    Trx addingPaymentMethod(PaymentRequestDto paymentRequestDto, String userEmail, String eventSlug, Long trxId);
    Trx getTrxDetail(Long trxId);
    List<Trx> getEventTrx(String eventSlug);
    List<Trx> getUserTrx(String email);
    String deleteUserTrx(String email, Long trxId);
}
