package com.pwdk.minpro_be.users.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwdk.minpro_be.roles.entity.Roles;
import com.pwdk.minpro_be.userRole.Entity.UserRole;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Generated;
import lombok.Getter;
import jakarta.persistence.*;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "users", schema = "public")
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull(message = "Name is required")
    @Size(max = 150)
    @Column(name = "name" , nullable = false)
    private String name;

    @NotNull(message = "Email is required")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @NotNull(message = "Password is required")
    @Size(max = 100)
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "refferal_code")
    private String refferalCode;

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
