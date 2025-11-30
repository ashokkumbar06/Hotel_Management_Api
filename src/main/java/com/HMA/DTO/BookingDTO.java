package com.HMA.DTO;

import com.HMA.Entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDTO {
    private String id;
    private User user;
    private int roomNo;
    private LocalDate checkIn;
    private LocalDate checkOut;

    @NotNull(message = "Base price is required")
    @Positive(message = "Base price must be positive")
    private double totalPrice;
    private String status;
}