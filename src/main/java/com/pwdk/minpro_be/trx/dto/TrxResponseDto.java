package com.pwdk.minpro_be.trx.dto;

import com.pwdk.minpro_be.event.dto.EventResponseDto;
import com.pwdk.minpro_be.trx.entity.PaymentStatus;
import com.pwdk.minpro_be.users.dto.UserDto;
import com.pwdk.minpro_be.vouchers.dto.VoucherResponseDto;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class TrxResponseDto {
    private Long id;
    private String invoiceNumber;
    private Double totalPrice;
    private Double totalDiscount;
    private Double finalPrice;
    private PaymentStatus paymentStatus;
    private PaymentMethodResponseDto paymentMethod;
    private UserDto user;
    private EventResponseDto event;
    private List<TrxItemResponseDto> trxItems;
    private List<VoucherResponseDto> vouchers;
    private Instant createdAt;
}
