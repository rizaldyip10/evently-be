package com.pwdk.minpro_be.trx.entity;

import com.pwdk.minpro_be.trx.dto.PaymentMethodResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Data
@Table(name = "payment_methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "Payment method name required")
    private String name;

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

    public PaymentMethodResponseDto toDto() {
        var responseDto = new PaymentMethodResponseDto();
        responseDto.setId(this.id);
        responseDto.setName(this.name);
        return responseDto;
    }
}
