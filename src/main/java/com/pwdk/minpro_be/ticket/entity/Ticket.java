package com.pwdk.minpro_be.ticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.ticket.dto.TicketResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Setter
@Getter
@Table(name = "ticket", schema = "public")
public class Ticket {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Name is required")
    private String name;

    @Column(name = "price", nullable = false)
    @NotNull(message = "price is required")
    private Float price;

    @Column(name = "description")
    private String description;

    @Column(name = "quota")
    private int quota;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @JsonIgnore
    @Column(name = "deleted_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant deletedAt;

    @Column(name = "created_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public TicketResponseDto toTicketDto() {
        TicketResponseDto responseDto = new TicketResponseDto();
        responseDto.setId(this.Id);
        responseDto.setName(this.name);
        responseDto.setPrice(this.price);
        responseDto.setDescription(this.description);
        responseDto.setQuota(this.quota);
        return responseDto;
    }
}
