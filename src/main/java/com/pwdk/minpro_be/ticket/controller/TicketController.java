package com.pwdk.minpro_be.ticket.controller;

import com.pwdk.minpro_be.responses.Response;
import com.pwdk.minpro_be.ticket.Service.TicketService;
import com.pwdk.minpro_be.ticket.dto.CreateTicketDto;
import com.pwdk.minpro_be.ticket.repository.TicketRepository;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ticket")
@Validated
@Log
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<?> createTicket (@RequestBody CreateTicketDto createTicketDto){
        return Response.success("Ticket success created" , ticketService.createTicket(createTicketDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Long id){
        return Response.success("Ticket detail", ticketService.getTicketById(id));
    }

    @GetMapping
    public ResponseEntity<?> findAllTickets(){
        return Response.success("tickets", ticketService.findAllTickets());
    }

    @GetMapping("/event/{eventSlug}")
    public ResponseEntity<?> findTicketByEventId(@PathVariable String eventSlug){
        return Response.success("Ticket Event" , ticketService.findTicketByEvent(eventSlug));
    }


}
