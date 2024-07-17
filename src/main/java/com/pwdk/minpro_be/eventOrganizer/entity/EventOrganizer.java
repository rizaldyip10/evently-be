package com.pwdk.minpro_be.eventOrganizer.entity;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.eventOrganizer.dto.EventOrganizerResponseDto;
import com.pwdk.minpro_be.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ManyToAny;

import java.time.Instant;

@Entity
@Setter
@Getter
@Table(name = "event_organizer", schema = "public")
public class EventOrganizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "deleted_at")
    private Instant deletedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    @PreRemove
    public void preRemove() {
        this.deletedAt = Instant.now();
    }

    public EventOrganizerResponseDto toDto() {
        var responseDto = new EventOrganizerResponseDto();
        responseDto.setId(this.id);
        responseDto.setEvent(this.getEvent().toDto());
        responseDto.setCreatedAt(this.createdAt);
        return responseDto;
    }
}
