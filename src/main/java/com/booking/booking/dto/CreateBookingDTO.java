package com.booking.booking.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateBookingDTO(
        @NotNull Long roomId,
        @NotNull Long userId,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate
        ) {
}
