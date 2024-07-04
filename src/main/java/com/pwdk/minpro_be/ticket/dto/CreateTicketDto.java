package com.pwdk.minpro_be.ticket.dto;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.ticket.entity.Ticket;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Data
public class CreateTicketDto {

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Price is required")
    private Float price;
    @NotBlank(message = "Quota is required")
    private int quota;
    @NotBlank(message = "Event is required")
    private Long event_id;

    public Ticket toEntity(){
        Ticket ticket = new Ticket();
        ticket.setName(name);
        ticket.setQuota(quota);
        ticket.setPrice(price);
        return ticket;
    }


}
