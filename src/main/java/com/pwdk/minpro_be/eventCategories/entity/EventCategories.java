package com.pwdk.minpro_be.eventCategories.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "event_categories", schema = "public")
public class EventCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @Column(name = "created_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant updatedAt;

    @JsonIgnore
    @Column(name = "deleted_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant deletedAt;
}
