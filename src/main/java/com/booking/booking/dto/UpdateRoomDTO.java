package com.booking.booking.dto;

import com.booking.booking.model.Room.RoomStatus;
import jakarta.validation.constraints.Size;

public record UpdateRoomDTO(
        String number,
        Integer capacity,
        Float pricePerNight,
        RoomStatus roomStatus,
        // STATUS
        @Size(min = 0, max = 255) String description
)
{ }