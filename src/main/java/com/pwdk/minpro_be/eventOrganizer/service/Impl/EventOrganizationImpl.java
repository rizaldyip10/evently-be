package com.pwdk.minpro_be.eventOrganizer.service.Impl;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.repository.EventRepository;
import com.pwdk.minpro_be.eventOrganizer.entity.EventOrganizer;
import com.pwdk.minpro_be.eventOrganizer.repository.EventOrganizationRepo;
import com.pwdk.minpro_be.eventOrganizer.service.EventOrganizationService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventOrganizationImpl implements EventOrganizationService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventOrganizationRepo eventOrganizationRepo;

    public EventOrganizationImpl(EventRepository eventRepository, UserRepository userRepository, EventOrganizationRepo eventOrganizationRepo){
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.eventOrganizationRepo = eventOrganizationRepo;
    }


    @Override
    public EventOrganizer createOrganization(Event eventId, User userId) {
        EventOrganizer newEvent = new EventOrganizer();
        Optional<Event> event = eventRepository.findById(eventId.getId());
        if(event.isEmpty()){
            throw new ApplicationException("Event not found");
        }
        newEvent.setEvent(event.get());
        newEvent.setId(newEvent.getId());

        Optional<User> user = userRepository.findById(userId.getId());
        if(user.isEmpty()){
            throw new ApplicationException("User not found");
        }
        newEvent.setUser(user.get());
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
}
