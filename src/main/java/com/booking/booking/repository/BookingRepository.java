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
            "WHERE :roomId = b.room.id " +
            "AND :startDate <= b.endDate " +
            "AND :endDate >= b.startDate " +
            "AND :bookingId != b.id" // nel controllo delle sovrapposizioni escludo la prenotazione corrente (con id=bookingId), se esiste.
    )
    boolean existsOverlappingBooking(
            @Param("bookingId") Long bookingId,
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
