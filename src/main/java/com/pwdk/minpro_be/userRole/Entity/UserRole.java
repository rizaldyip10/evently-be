package com.pwdk.minpro_be.userRole.Entity;

import com.pwdk.minpro_be.roles.entity.Roles;
import com.pwdk.minpro_be.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.management.relation.Role;
import java.time.Instant;

@Entity
@Setter
@Getter
@Table(name = "user_roles", schema = "public")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    @JoinColumn(name = "role_id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private Roles role;

    @Column(name = "created_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant deleted_at;

    @PrePersist
    protected void onCreate(){
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
