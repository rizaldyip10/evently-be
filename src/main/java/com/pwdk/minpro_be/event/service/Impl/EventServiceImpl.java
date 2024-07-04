package com.pwdk.minpro_be.event.service.Impl;

import com.pwdk.minpro_be.event.dto.CreateEventDto;
import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.repository.EventRepository;
import com.pwdk.minpro_be.event.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @Override
    public Event createEvent(CreateEventDto event) {
        Event newEvent = event.toEntity();
        return eventRepository.save(newEvent);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }


    @Override
    public void deleted_at(Long Id) {

    }

    @Override
    public Event findByUser(Event event) {
        return null;
    }


}
