package com.pwdk.minpro_be.trx.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentRequestDto {
    private String paymentMethod;
    private List<VoucherPaymentDto> vouchers;
    private Boolean isPointUsed;
}
