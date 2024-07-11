package com.pwdk.minpro_be.ticket.Service;

import com.pwdk.minpro_be.ticket.dto.CreateTicketDto;
import com.pwdk.minpro_be.ticket.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    Ticket createTicket (CreateTicketDto ticket);
    Optional<Ticket> getTicketById(Long id);
    List<Ticket> findAllTickets();
    List<Ticket> findTicketByEventId(Long eventId);
    void updateTicketQuota(Long ticketId, int updatedQuota);
}
