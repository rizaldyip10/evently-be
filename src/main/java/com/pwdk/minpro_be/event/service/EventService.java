package com.pwdk.minpro_be.event.service;

import com.pwdk.minpro_be.event.dto.CreateEventDto;
import com.pwdk.minpro_be.event.dto.EventResponseDto;
import com.pwdk.minpro_be.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public interface EventService {
    Event createEvent(CreateEventDto event, String userEmail);
    Page<EventResponseDto> findAllEvent(Pageable pageable,
                                        String searchedEventName,
                                        String searchedCategory,
                                        String searchedCity,
                                        Date searchedDate);
    void deleteEvent(Long eventId);
    Event findByUser (Event event);

    Event findBySlug(String eventSlug);



}
