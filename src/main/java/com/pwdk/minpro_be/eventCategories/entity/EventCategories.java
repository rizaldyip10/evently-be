package com.pwdk.minpro_be.eventCategories.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "id", nullable = false)
    private Long Id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant created_at;

    @Column(name = "updated_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant updated_at;

    @Column(name = "deleted_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant deleted_at;
}
