package com.pwdk.minpro_be.event.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.users.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;

import java.util.Date;

@Setter
@Getter
@Data
public class CreateEventDto {

    @NotBlank(message = "Event name is required")
    private String name;

    @NotBlank(message = "Event category is required")
    private Long eventCategoryId;

    @NotBlank(message = "Event date is required")
    private Date date;

    @NotBlank(message = "Event description is required")
    private String description;

    @NotBlank(message = "Event location is required")
    private String location;

    private String city;
    private String audiance_info;
    private String attention_info;
    private String event_img;

    @NotBlank(message = "Event start time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Date start_time;

    @NotBlank(message = "Event end time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Date end_time;



    public Event toEntity(){
        Event event = new Event();
        event.setName(name);
        event.setEventCategoryId(eventCategoryId);
        event.setDate(date);
        event.setDescription(description);
        event.setLocation(location);
        event.setCity(city);
        event.setAudianceInfo(audiance_info);
        event.setAttentionInfo(attention_info);
        event.setEventImage(event_img);
        event.setStartTime(start_time);
        event.setEndTime(end_time);
        return event;
    }
}
