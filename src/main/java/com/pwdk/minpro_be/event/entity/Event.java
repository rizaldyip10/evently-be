package com.pwdk.minpro_be.event.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.type.descriptor.jdbc.TimestampWithTimeZoneJdbcType;


import java.time.Instant;
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
    private Date start_time;

    @Column(name = "end_time", nullable = false)
    private Date end_time;

    @Column(name = "audiance_info", nullable = false)
    private String audiance_info;

    @Column(name = "attention_info", nullable = false)
    private String attention_info;

    @Column(name = "event_category_id", nullable = false)
    private Long event_category_id;

    @Column(name = "event_image", nullable = false)
    private String event_image;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant created_at;

    @Column(name = "updated_at", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant updated_at;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "deleted_at", nullable = false)
    private Instant deleted_at;

    @PrePersist
    protected void onCreate(){
        this.created_at = Instant.now();
        this.updated_at = Instant.now();
    }


}
