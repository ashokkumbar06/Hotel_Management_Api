package com.HMA.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RoomDto {

    private String id;

    @NotBlank(message = "Room number is required")
    @Pattern(regexp = "^[A-Za-z0-9-]+$",
            message = "Room number can contain letters, numbers, and hyphens only")
    private String roomNumber;

    @NotBlank(message = "Room type is required")
    @Pattern(regexp = "^(SUITE|STANDARD|DELUXE)$",
            message = "Room type must be SUITE, STANDARD, or DELUXE")
    private String roomType;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @Positive(message = "Price per day must be greater than 0")
    private double pricePerDay;
}