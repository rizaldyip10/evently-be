package com.pwdk.minpro_be.trx.entity;

import com.pwdk.minpro_be.event.entity.Event;
import com.pwdk.minpro_be.users.entity.User;
import com.pwdk.minpro_be.vouchers.entity.Voucher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "transactions")
public class Trx {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "Invoice Number required")
    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_discount")
    private Double totalDiscount;

    @Column(name = "final_price")
    private Double finalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_status_id")
    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "trx", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrxItem> trxItems;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "transaction_promos",
            joinColumns = @JoinColumn(name = "trx_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id"))
    private List<Voucher> vouchers;

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
}
