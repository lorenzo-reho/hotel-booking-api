package com.booking.booking.dto;

import com.booking.booking.model.Room;
import com.booking.booking.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO (
        String firstName,
        String lastName,
        User.Role role
        ) {
}
