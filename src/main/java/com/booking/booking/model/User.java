package com.booking.booking.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="users")
public class User {

    public enum Role{
        USER,
        ADMIN
    };

    public User(){
        
    }

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
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;

    private boolean enabled = true;
    private boolean emailVerified = true;

    @Column(nullable = false,
            updatable = false,
            insertable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    @Generated(event = EventType.INSERT)
    private Instant createdAt;

    @Column(nullable = false,
            insertable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    @Generated(event = EventType.INSERT)
    private Instant updatedAt;

    @OneToMany(mappedBy="user", cascade = CascadeType.REMOVE)
    Set<Booking> bookings;


}
