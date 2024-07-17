package com.pwdk.minpro_be.vouchers.entity;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.vouchers.dto.EventVoucherResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Data
@Table(name = "event_vouchers")
public class EventVoucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "expired_at", nullable = false)
    private Instant expiredAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

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

    public EventVoucherResponseDto toDto() {
        EventVoucherResponseDto responseDto = new EventVoucherResponseDto();
        responseDto.setId(this.id);
        responseDto.setVoucher(this.voucher.toDto());
        responseDto.setEvent(this.event.toDto());
        responseDto.setCreatedAt(this.createdAt);
        return responseDto;
    }
}
