package com.pwdk.minpro_be.vouchers.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreateVoucherRequestDto {
    private String name;
    private BigDecimal discount;
    private Date expiredDate;
}
