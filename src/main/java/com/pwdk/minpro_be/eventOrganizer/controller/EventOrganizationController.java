package com.pwdk.minpro_be.eventOrganizer.controller;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.event.service.EventService;
import com.pwdk.minpro_be.eventOrganizer.entity.EventOrganizer;
import com.pwdk.minpro_be.eventOrganizer.service.EventOrganizationService;
import com.pwdk.minpro_be.responses.Response;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.users.service.UserService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/event-organization")
@Validated
@Log
public class EventOrganizationController {

    private final EventOrganizationService eventOrganizationService;
    private final UserService userService;
    private final EventService eventService;


    public EventOrganizationController(EventOrganizationService eventOrganizationService, UserService userService, EventService eventService){
        this.eventService = eventService;
        this.userService = userService;
        this.eventOrganizationService = eventOrganizationService;
    }

    @PostMapping
    public ResponseEntity<?> createOrganization(@RequestBody Event event, User user){
        return Response.success("Event Organization successfully created" , eventOrganizationService.createOrganization(event, user));
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return Response.success("Event Organizations" , eventOrganizationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(Long id){
        return Response.success("Event Organization", eventOrganizationService.findById(id));
    }
}
