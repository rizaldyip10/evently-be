package com.pwdk.minpro_be.eventOrganizer.service;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.eventOrganizer.entity.EventOrganizer;
import com.pwdk.minpro_be.users.entity.User;

import java.util.List;

public interface EventOrganizationService {
    EventOrganizer createOrganization (Event eventId, User userId);
    List<EventOrganizer> findAll();
    EventOrganizer findById(Long id);
}
