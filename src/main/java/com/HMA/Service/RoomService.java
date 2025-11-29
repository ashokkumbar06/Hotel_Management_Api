package com.HMA.Service;

import com.HMA.DTO.RoomDto;
import com.HMA.Entity.Room;

import java.util.List;

public interface RoomService {

    RoomDto saveRoom(RoomDto room);
    Room updateRoom(Integer id, Room room);
    List<Room> getAllRooms();
    Room getRoomById(Integer id);
    String deleteRoom(Integer id);
}
