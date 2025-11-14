package com.HMA.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rooms")
public class Room {
    @Id
    private String id;
    private String roomNumber;
    private String type;
    private int capacity;
    private double pricePerDay;
}