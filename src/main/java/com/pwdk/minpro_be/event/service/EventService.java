package com.pwdk.minpro_be.event.service;

import com.pwdk.minpro_be.event.dto.CreateEventDto;
import com.pwdk.minpro_be.event.entity.Event;

import java.util.List;

public interface EventService {
    Event createEvent(CreateEventDto event);
    List<Event> findAll();
    void deleted_at(Long Id);
    Event findByUser (Event event);

    Event findBySlug(String eventSlug);



}
