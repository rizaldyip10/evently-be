package com.pwdk.minpro_be.ticket.Service.Impl;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.repository.EventRepository;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.responses.Response;
import com.pwdk.minpro_be.ticket.Service.TicketService;
import com.pwdk.minpro_be.ticket.dto.CreateTicketDto;
import com.pwdk.minpro_be.ticket.entity.Ticket;
import com.pwdk.minpro_be.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;


    public TicketServiceImpl(TicketRepository ticketRepository, EventRepository eventRepository){
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    public Ticket createTicket(CreateTicketDto ticket){
        Ticket newTicket = ticket.toEntity();
        Optional<Event> event = eventRepository.findById(ticket.getEvent_id());
        if(event.isEmpty()){
            throw new ApplicationException("Event not found");
        }
        newTicket.setEvent(event.get());
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
    public List<Ticket> findTicketByEventId(Long eventId) {
        return ticketRepository.findByEventId(eventId);
    }


}
