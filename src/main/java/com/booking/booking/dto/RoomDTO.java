package com.booking.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RoomDTO(
        @NotBlank String number,
        int capacity,
        float pricePerNight,
        // STATUS
        @Size(min = 0, max = 255) String description
        )
{ }
