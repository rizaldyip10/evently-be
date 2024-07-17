package com.pwdk.minpro_be.eventOrganizer.service;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.eventOrganizer.dto.EventOrganizerResponseDto;
import com.pwdk.minpro_be.eventOrganizer.entity.EventOrganizer;
import com.pwdk.minpro_be.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventOrganizationService {
    EventOrganizer createOrganization (Event eventId, User userId);
    List<EventOrganizer> findAll();
    EventOrganizer findById(Long id);
    Page<EventOrganizerResponseDto> getUserEvent(Pageable pageable, String userEmail);
}
