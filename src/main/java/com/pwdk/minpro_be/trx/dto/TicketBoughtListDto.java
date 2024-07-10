package com.pwdk.minpro_be.trx.dto;

import lombok.Data;

@Data
public class TicketBoughtListDto {
    private Long id;
    private String name;
    private Double price;
    private Integer amount;
}
