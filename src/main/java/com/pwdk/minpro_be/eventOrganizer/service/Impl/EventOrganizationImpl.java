package com.pwdk.minpro_be.eventOrganizer.service.Impl;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.repository.EventRepository;
import com.pwdk.minpro_be.event.service.EventService;
import com.pwdk.minpro_be.eventOrganizer.dto.EventOrganizerResponseDto;
import com.pwdk.minpro_be.eventOrganizer.entity.EventOrganizer;
import com.pwdk.minpro_be.eventOrganizer.repository.EventOrganizationRepo;
import com.pwdk.minpro_be.eventOrganizer.service.EventOrganizationService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.repository.UserRepository;
import com.pwdk.minpro_be.users.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventOrganizationImpl implements EventOrganizationService {
    private final EventService eventService;
    private final UserService userService;
    private final EventOrganizationRepo eventOrganizationRepo;

    public EventOrganizationImpl(
            @Lazy EventService eventService,
            @Lazy UserService userService,
            EventOrganizationRepo eventOrganizationRepo){
        this.eventService = eventService;
        this.userService = userService;
        this.eventOrganizationRepo = eventOrganizationRepo;
    }


    @Override
    public EventOrganizer createOrganization(Event eventId, User userId) {
        EventOrganizer newEvent = new EventOrganizer();
        Event event = eventService.findById(eventId.getId());
        newEvent.setEvent(event);
        newEvent.setId(newEvent.getId());

        User user = userService.findById(userId.getId());
        newEvent.setUser(user);
        newEvent.setId(userId.getId());
        eventOrganizationRepo.save(newEvent);


        return newEvent;
    }

    @Override
    public List<EventOrganizer> findAll() {
        return eventOrganizationRepo.findAll();
    }

    @Override
    public EventOrganizer findById(Long id) {
        return eventOrganizationRepo.findById(id).orElseThrow(() -> new ApplicationException("Event not found"));
    }

    @Override
    public Page<EventOrganizerResponseDto> getUserEvent(Pageable pageable, String userEmail) {
        User user = userService.findByEmail(userEmail);
        return eventOrganizationRepo.findByUserId(pageable, user.getId()).map(EventOrganizer::toDto);
    }
}
