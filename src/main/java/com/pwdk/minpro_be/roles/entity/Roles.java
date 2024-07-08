package com.pwdk.minpro_be.roles.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwdk.minpro_be.userRole.Entity.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Setter
@Getter
@Entity
@Table(name = "roles", schema = "public")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    @JsonIgnore
    private Instant createdAt;

    @Column(name = "updated_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @JsonIgnore
    private Instant updatedAt;

    @Column(name = "deleted_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @JsonIgnore
    private Instant deletedAt;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "role")
    private Set<UserRole> userRoles;

    @PrePersist
    protected void onCreate(){
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

}
