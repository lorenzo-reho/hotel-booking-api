package com.booking.booking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="rooms")
public class Room {

    public enum RoomStatus{
        AVAILABLE,
        MAINTENANCE,
        OUT_OF_SERVICE
    };

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    private String description;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private float pricePerNight;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status = RoomStatus.OUT_OF_SERVICE;

    @OneToMany(mappedBy = "room")
    Set<Booking> bookings;
}
