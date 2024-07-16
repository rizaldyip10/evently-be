package com.pwdk.minpro_be.ticket.Service.Impl;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.repository.EventRepository;
import com.pwdk.minpro_be.event.service.EventService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.exception.DataNotFoundException;
import com.pwdk.minpro_be.responses.Response;
import com.pwdk.minpro_be.ticket.Service.TicketService;
import com.pwdk.minpro_be.ticket.dto.CreateTicketDto;
import com.pwdk.minpro_be.ticket.dto.TicketResponseDto;
import com.pwdk.minpro_be.ticket.entity.Ticket;
import com.pwdk.minpro_be.ticket.repository.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final EventService eventService;


    public TicketServiceImpl(TicketRepository ticketRepository, EventRepository eventRepository, EventService eventService){
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }

    public Ticket createTicket(CreateTicketDto ticket){
        Ticket newTicket = ticket.toEntity();
        Event event = eventService.findById(ticket.getEvent_id());
        newTicket.setEvent(event);
        newTicket.setId(ticket.getEvent_id());
        ticketRepository.save(newTicket);
        return newTicket;
    }

    public Optional<Ticket> getTicketById(Long id){
        return ticketRepository.findById(id);
    }

    public List<Ticket> findAllTickets(){
        return ticketRepository.findAll();
    }

    @Override
    public List<TicketResponseDto> findTicketByEvent(String eventSlug) {
        Event event = eventService.findBySlug(eventSlug);
        return ticketRepository.findByEventIdAndDeletedAtIsNull(event.getId()).stream()
                .map(Ticket::toTicketDto).collect(Collectors.toList());
    }

    @Override
    public void updateTicketQuota(Long ticketId, int updatedQuota) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "Ticket not found"));

        ticket.setQuota(updatedQuota);
        ticketRepository.save(ticket);
    }


}
