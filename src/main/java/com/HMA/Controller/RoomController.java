package com.HMA.Controller;

import com.HMA.Entity.Room;
import com.HMA.Service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@Tag(name = "Rooms Operations", description = "Operations related to hotel rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> saveRoom(@RequestBody Room room) {
        Room data = roomService.saveRoom(room);
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable Integer id, @RequestBody Room room) {
        return roomService.updateRoom(id, room);
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Integer id) {
        return roomService.getRoomById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        return roomService.deleteRoom(id);
    }
}