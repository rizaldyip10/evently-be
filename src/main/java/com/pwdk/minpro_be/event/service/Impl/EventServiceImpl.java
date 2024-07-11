package com.pwdk.minpro_be.event.service.Impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.pwdk.minpro_be.event.dto.CreateEventDto;
import com.pwdk.minpro_be.event.dto.EventResponseDto;
import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.helper.SlugGen;
import com.pwdk.minpro_be.event.repository.EventRepository;
import com.pwdk.minpro_be.event.service.EventService;
import com.pwdk.minpro_be.eventCategories.repository.EventCategoryRepo;
import com.pwdk.minpro_be.eventOrganizer.entity.EventOrganizer;
import com.pwdk.minpro_be.eventOrganizer.service.EventOrganizationService;
import com.pwdk.minpro_be.exception.ApplicationException;
import com.pwdk.minpro_be.exception.DataNotFoundException;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final SlugGen slugGen;
    private final EventOrganizationService eventOrganizationService;
    private final UserService userService;
    private final EventCategoryRepo eventCategoryRepo;

    public EventServiceImpl(EventRepository eventRepository, SlugGen slugGen, EventOrganizationService eventOrganizationService, UserService userService, EventCategoryRepo eventCategoryRepo) {
        this.eventRepository = eventRepository;
        this.slugGen = slugGen;
        this.eventOrganizationService = eventOrganizationService;
        this.userService = userService;
        this.eventCategoryRepo = eventCategoryRepo;
    }


    @Transactional
    @Override
    public Event createEvent(CreateEventDto event, String userEmail) {
        Event newEvent = event.toEntity();
        String eventSlug = slugGen.slugGenerator(event.getName());
        var isSlugExist = eventRepository.findBySlug(eventSlug);
        if (isSlugExist.isPresent()) {
            var random = new Random();
            char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'};
            eventSlug = eventSlug + NanoIdUtils.randomNanoId(random, alphabet, 12);
        }
        newEvent.setSlug(eventSlug);
        var category = eventCategoryRepo.findById(event.getEventCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        newEvent.setEventCategory(category);
        var createdEvent = eventRepository.save(newEvent);

        User user = userService.findByEmail(userEmail);
        eventOrganizationService.createOrganization(newEvent, user);

        return createdEvent;
    }

    @Override
    public Page<EventResponseDto> findAllEvent(Pageable pageable) {
        return eventRepository.findAllAndDeletedAtIsNull(pageable)
                .map(Event::toDto);
    }


    @Override
    public void deleteEvent(Long eventId) {

    }

    @Override
    public Event findByUser(Event event) {
        return null;
    }

    @Override
    public Event findBySlug(String eventSlug) {
        Optional<Event> event = eventRepository.findBySlug(eventSlug);
        if (event.isEmpty()) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Event not found");
        }
        return event.get();
    }


}
