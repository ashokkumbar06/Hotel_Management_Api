package com.HMA.DTO;

import com.HMA.Entity.User;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDTO {

    private String id;

    @NotNull(message = "User details are required")
    private User user;

    @Min(value = 1, message = "Room number must be at least 1")
    private int roomNo;

    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date cannot be in the past")
    private LocalDate checkIn;

    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out date must be a future date")
    private LocalDate checkOut;

    @NotNull(message = "Total price is required")
    @Positive(message = "Total price must be positive")
    private Double totalPrice;

    @Pattern(regexp = "^(CONFIRMED|CANCELLED|COMPLETED)$",
            message = "Status must be CONFIRMED, CANCELLED, or COMPLETED")
    private String status;
}