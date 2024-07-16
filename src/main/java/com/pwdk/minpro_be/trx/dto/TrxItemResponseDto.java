package com.pwdk.minpro_be.trx.dto;

import com.pwdk.minpro_be.ticket.dto.TicketResponseDto;
import lombok.Data;

import java.time.Instant;

@Data
public class TrxItemResponseDto {
    private Long id;
    private TicketResponseDto ticket;
    private int amount;
}
