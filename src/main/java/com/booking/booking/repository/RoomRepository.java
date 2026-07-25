package com.booking.booking.repository;


import com.booking.booking.model.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
            "SELECT r " +
            "FROM Room r " +
            "WHERE r.id = :id"
    )
    Room findForUpdate(@Param("id") Long id);

    boolean existsByNumber(String number);

}
