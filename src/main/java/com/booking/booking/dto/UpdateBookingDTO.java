package com.booking.booking.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateBookingDTO(@NotNull LocalDate startDate,
                               @NotNull LocalDate endDate) {
}
