package com.HMA.Service;

import com.HMA.Entity.Room;

import java.util.List;

public interface RoomService {

    Room saveRoom(Room room);
    Room updateRoom(Integer id, Room room);
    List<Room> getAllRooms();
    Room getRoomById(Integer id);
    String deleteRoom(Integer id);
}
