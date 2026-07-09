package com.booking.booking.model;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.Instant;

@Getter
@Entity
@Table(name="users")
public class User {

    public enum Role{
        USER,
        ADMIN
    };

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled;
    private boolean emailVerified;

    private Instant createdAt;
    private Instant updatedAt;


}
