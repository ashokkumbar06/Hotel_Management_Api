package com.HMA.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RoomDto {
    private String id;
    private String roomNumber;
    @Pattern(regexp = "^(SUITE|STANDARD|DELUXE)$", message = "Room type must be SUITE, STANDARD, or DELUXE")
    @NotBlank(message = "Room number is required")
    private String roomType;
    private int capacity;
    private double pricePerDay;
}