package com.HMA.Mapper;

import com.HMA.DTO.RoomDto;
import com.HMA.Entity.Room;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    @Autowired
    private ModelMapper modelMapper;

    public RoomDto toDTO(Room room) {
        RoomDto dto = modelMapper.map(room, RoomDto.class);
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setType(room.getType());
        dto.setCapacity(room.getCapacity());
        dto.setPricePerDay(room.getPricePerDay());
        return dto;
    }

    public Room toEntity(RoomDto dto) {
        return modelMapper.map(dto, Room.class);
    }
}