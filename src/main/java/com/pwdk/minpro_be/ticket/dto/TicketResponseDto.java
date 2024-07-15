package com.pwdk.minpro_be.ticket.dto;

import lombok.Data;

@Data
public class TicketResponseDto {
    private Long id;
    private String name;
    private Float price;
    private String description;
    private int quota;
}
