package com.pwdk.minpro_be.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pwdk.minpro_be.eventCategories.entity.EventCategories;
import com.pwdk.minpro_be.ticket.dto.TicketResponseDto;
import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
public class EventResponseDto {
    private String name;
    private String slug;
    private String location;
    private String description;
    private String city;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Date endTime;
    private EventCategories eventCategories;
    private String audienceInfo;
    private String attentionInfo;
    private String eventImg;
    private Instant createdAt;
    private Instant updatedAt;
    private List<TicketResponseDto> tickets;
}
