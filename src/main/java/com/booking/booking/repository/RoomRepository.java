package com.booking.booking.repository;


import com.booking.booking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByNumber(String number);
}
