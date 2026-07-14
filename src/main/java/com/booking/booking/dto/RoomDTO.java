package com.booking.booking.dto;

import com.booking.booking.model.Room;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoomDTO(
        @NotNull @NotBlank String number,
        @NotNull Integer capacity,
        @NotNull Float pricePerNight,
        @NotNull Room.RoomStatus roomStatus,
        // STATUS
        @NotNull @Size(min = 0, max = 255) String description
        )
{ }
