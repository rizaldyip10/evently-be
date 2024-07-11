package com.pwdk.minpro_be.trx.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VoucherPaymentDto {
    private String name;
    private BigDecimal discount;
}
