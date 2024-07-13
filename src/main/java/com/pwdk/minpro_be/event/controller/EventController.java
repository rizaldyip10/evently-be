package com.pwdk.minpro_be.event.controller;

import com.pwdk.minpro_be.auth.helpers.Claims;
import com.pwdk.minpro_be.event.dto.CreateEventDto;
import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.service.EventService;
import com.pwdk.minpro_be.responses.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventDto createEventDto){
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");

        return Response.success(HttpStatus.CREATED.value(), "Event successfully created" , eventService.createEvent(createEventDto, email));

    }

    @GetMapping
    public ResponseEntity<?> findAllEvent(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Date date
            ){
        return Response.success("Event list fetched",
                eventService.findAllEvent(PageRequest.of(page, size), name, category, city, date));
    }
}
