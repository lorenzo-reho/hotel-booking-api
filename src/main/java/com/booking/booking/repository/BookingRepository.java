package com.booking.booking.repository;

import com.booking.booking.model.Booking;
import com.booking.booking.model.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT COUNT(b) > 0 " +
            "FROM Booking b " +
            "WHERE b.room.id = :roomId " +
            "AND :startDate <= b.endDate " +
            "AND :endDate >= b.startDate")
    boolean existsOverlappingBooking(@Param("roomId") Long roomId,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);
}
