package com.pwdk.minpro_be.trx.service;

import com.pwdk.minpro_be.trx.dto.PaymentRequestDto;
import com.pwdk.minpro_be.trx.dto.TicketTrxRequestDto;
import com.pwdk.minpro_be.trx.dto.TrxResponseDto;
import com.pwdk.minpro_be.trx.entity.Trx;

import java.util.List;

public interface TrxService {
    TrxResponseDto createUserTrx(String userEmail, String eventSlug);
    TrxResponseDto addingTrxItem(TicketTrxRequestDto ticketTrxRequestDto, String userEmail, String eventSlug, Long trxId);
    TrxResponseDto addingPaymentMethod(PaymentRequestDto paymentRequestDto, String userEmail, String eventSlug, Long trxId);
    TrxResponseDto getTrxDetail(Long trxId);
    List<TrxResponseDto> getEventTrx(String eventSlug);
    List<TrxResponseDto> getUserTrx(String email);
    String deleteUserTrx(String email, Long trxId);
}
