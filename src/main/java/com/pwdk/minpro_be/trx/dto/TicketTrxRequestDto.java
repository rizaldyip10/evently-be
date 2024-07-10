package com.pwdk.minpro_be.trx.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketTrxRequestDto {
    private List<TicketBoughtListDto> ticketList;
}
