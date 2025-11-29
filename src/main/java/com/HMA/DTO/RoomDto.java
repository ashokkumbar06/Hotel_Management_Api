package com.HMA.DTO;

import lombok.Data;

@Data
public class RoomDto {
    private String id;
    private String roomNumber;
    private String type;
    private int capacity;
    private double pricePerDay;
}