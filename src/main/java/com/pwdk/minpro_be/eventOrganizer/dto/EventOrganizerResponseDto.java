package com.pwdk.minpro_be.eventOrganizer.dto;

import com.pwdk.minpro_be.event.dto.EventResponseDto;
import lombok.Data;

import java.time.Instant;

@Data
public class EventOrganizerResponseDto {
    private Long id;
    private EventResponseDto event;
    private Instant createdAt;
}
