package com.pwdk.minpro_be.event.controller;

import com.pwdk.minpro_be.event.dto.CreateEventDto;
import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.service.EventService;
import com.pwdk.minpro_be.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return Response.success("Event successfully created" , eventService.createEvent(createEventDto));

    }

    @GetMapping
    public ResponseEntity<Response<List<Event>>> findAll(){
        return Response.success("Event created", eventService.findAll());
    }
}
