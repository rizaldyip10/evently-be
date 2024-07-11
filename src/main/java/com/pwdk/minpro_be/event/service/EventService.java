package com.pwdk.minpro_be.event.service;

import com.pwdk.minpro_be.event.dto.CreateEventDto;
import com.pwdk.minpro_be.event.entity.Event;

import java.util.List;

public interface EventService {
    Event createEvent(CreateEventDto event, String userEmail);
    List<Event> findAllEvent();
    void deleteEvent(Long eventId);
    Event findByUser (Event event);

    Event findBySlug(String eventSlug);



}
