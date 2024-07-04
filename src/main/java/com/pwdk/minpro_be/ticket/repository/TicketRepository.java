package com.pwdk.minpro_be.ticket.repository;

import com.pwdk.minpro_be.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByeventId(Long eventId);
}
