package com.pwdk.minpro_be.vouchers.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class VoucherResponseDto {
    private Long id;
    private String name;
    private BigDecimal discount;
    private Instant createdAt;
}
