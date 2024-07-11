package com.pwdk.minpro_be.event.entity;

import com.pwdk.minpro_be.event.dto.EventResponseDto;
import com.pwdk.minpro_be.eventCategories.entity.EventCategories;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;


import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "events", schema = "public")
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long Id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "event_id")
//    private Event event;

    @NotNull(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @Column(name = "audiance_info", nullable = false)
    private String audianceInfo;

    @Column(name = "attention_info", nullable = false)
    private String attentionInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_category_id", nullable = false)
    private EventCategories eventCategory;

    @Column(name = "event_image", nullable = false)
    private String eventImage;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant updatedAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "deleted_at", nullable = false)
    private Instant deletedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
        this.updatedAt = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
    }

    @PreRemove
    public void preRemove() {
        this.deletedAt = Instant.now().atZone(ZoneId.systemDefault()).toInstant();
    }

    public EventResponseDto toDto() {
        EventResponseDto responseDto = new EventResponseDto();
        responseDto.setName(this.name);
        responseDto.setSlug(this.slug);
        responseDto.setDate(this.date);
        responseDto.setDescription(this.description);
        responseDto.setLocation(this.location);
        responseDto.setCity(this.city);
        responseDto.setAttentionInfo(this.attentionInfo);
        responseDto.setAudienceInfo(this.audianceInfo);
        responseDto.setEventImg(this.eventImage);
        responseDto.setStartTime(this.startTime);
        responseDto.setEndTime(this.endTime);
        responseDto.setUpdatedAt(this.updatedAt);
        responseDto.setCreatedAt(this.createdAt);
        responseDto.setEventCategories(this.eventCategory);

        return responseDto;
    }

}
