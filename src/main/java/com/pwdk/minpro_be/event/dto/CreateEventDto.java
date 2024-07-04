package com.pwdk.minpro_be.event.dto;


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
    private Date start_time;

    @NotBlank(message = "Event end time is required")
    private Date end_time;



    public Event toEntity(){
        Event event = new Event();
        event.setName(name);
        event.setEvent_category_id(eventCategoryId);
        event.setDate(date);
        event.setDescription(description);
        event.setLocation(location);
        event.setCity(city);
        event.setAudiance_info(audiance_info);
        event.setAttention_info(attention_info);
        event.setEvent_image(event_img);
        event.setStart_time(start_time);
        event.setEnd_time(end_time);
        return event;
    }
}
