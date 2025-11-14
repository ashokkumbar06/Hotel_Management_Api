package com.HMA.Service;

import com.HMA.Entity.Room;
import com.HMA.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Integer id, Room room) {
        Room existing = roomRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Room not found"));

        existing.setRoomNumber(room.getRoomNumber());
        existing.setType(room.getType());
        existing.setCapacity(room.getCapacity());
        existing.setPricePerDay(room.getPricePerDay());

        return roomRepository.save(existing);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Integer id) {
        return roomRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @Override
    public String deleteRoom(Integer id) {
        roomRepository.deleteById(String.valueOf(id));
        return "Room deleted successfully";
    }
}
