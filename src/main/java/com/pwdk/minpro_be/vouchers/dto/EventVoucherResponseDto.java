package com.pwdk.minpro_be.vouchers.dto;

import com.pwdk.minpro_be.event.dto.EventResponseDto;
import lombok.Data;

import java.time.Instant;

@Data
public class EventVoucherResponseDto {
    private Long id;
    private EventResponseDto event;
    private VoucherResponseDto voucher;
    private Instant createdAt;
}
