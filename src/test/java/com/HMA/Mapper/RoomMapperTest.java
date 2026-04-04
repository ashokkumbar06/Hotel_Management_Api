package com.HMA.Mapper;

import com.HMA.DTO.RoomDto;
import com.HMA.Entity.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RoomMapperTest {

    @Autowired
    private RoomMapper roomMapper;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void shouldMapRoomToDTOWithAllFields() {
        Room room = new Room();
        room.setId("room1");
        room.setRoomNumber("101");
        room.setRoomType("STANDARD");
        room.setCapacity(2);
        room.setPricePerDay(100.0);
        RoomDto mappedDTO = new RoomDto();
        mappedDTO.setId("room1");
        mappedDTO.setRoomNumber("101");
        mappedDTO.setRoomType("STANDARD");
        mappedDTO.setCapacity(2);
        mappedDTO.setPricePerDay(100.0);
        when(modelMapper.map(room, RoomDto.class)).thenReturn(mappedDTO);
        RoomDto result = roomMapper.toDTO(room);
        assertNotNull(result);
        assertEquals("room1", result.getId());
        assertEquals("101", result.getRoomNumber());
        assertEquals("STANDARD", result.getRoomType());
        assertEquals(2, result.getCapacity());
        assertEquals(100.0, result.getPricePerDay());
    }

    @Test
    void shouldMapRoomToDTOWithNullRoomType() {
        Room room = new Room();
        room.setId("room1");
        room.setRoomNumber("101");
        room.setCapacity(2);
        room.setPricePerDay(100.0);
        RoomDto mappedDTO = new RoomDto();
        mappedDTO.setId("room1");
        mappedDTO.setRoomNumber("101");
        mappedDTO.setRoomType(null);
        mappedDTO.setCapacity(2);
        mappedDTO.setPricePerDay(100.0);
        when(modelMapper.map(room, RoomDto.class)).thenReturn(mappedDTO);
        RoomDto result = roomMapper.toDTO(room);
        assertNotNull(result);
        assertNull(result.getRoomType());
        assertEquals("101", result.getRoomNumber());
    }

    @Test
    void shouldMapRoomToDTOWithDifferentRoomTypes() {
        Room room = new Room();
        room.setId("room2");
        room.setRoomNumber("202");
        room.setRoomType("DELUXE");
        room.setCapacity(4);
        room.setPricePerDay(200.0);

        RoomDto mappedDTO = new RoomDto();
        mappedDTO.setId("room2");
        mappedDTO.setRoomNumber("202");
        mappedDTO.setRoomType("DELUXE");
        mappedDTO.setCapacity(4);
        mappedDTO.setPricePerDay(200.0);
        when(modelMapper.map(room, RoomDto.class)).thenReturn(mappedDTO);
        RoomDto result = roomMapper.toDTO(room);
        assertNotNull(result);
        assertEquals("DELUXE", result.getRoomType());
        assertEquals(4, result.getCapacity());
        assertEquals(200.0, result.getPricePerDay());
    }

    @Test
    void shouldMapDTOToEntityWithAllFields() {
        RoomDto roomDto = new RoomDto();
        roomDto.setId("room1");
        roomDto.setRoomNumber("101");
        roomDto.setRoomType("STANDARD");
        roomDto.setCapacity(2);
        roomDto.setPricePerDay(100.0);

        Room mappedRoom = new Room();
        mappedRoom.setId("room1");
        mappedRoom.setRoomNumber("101");
        mappedRoom.setRoomType("STANDARD");
        mappedRoom.setCapacity(2);
        mappedRoom.setPricePerDay(100.0);

        when(modelMapper.map(roomDto, Room.class)).thenReturn(mappedRoom);

        Room result = roomMapper.toEntity(roomDto);

        assertNotNull(result);
        assertEquals("room1", result.getId());
        assertEquals("101", result.getRoomNumber());
        assertEquals("STANDARD", result.getRoomType());
        assertEquals(2, result.getCapacity());
        assertEquals(100.0, result.getPricePerDay());
    }

    @Test
    void shouldMapDTOToEntityWithNullId() {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(null);
        roomDto.setRoomNumber("101");
        roomDto.setRoomType("STANDARD");
        roomDto.setCapacity(2);
        roomDto.setPricePerDay(100.0);

        Room mappedRoom = new Room();
        mappedRoom.setId(null);
        mappedRoom.setRoomNumber("101");
        mappedRoom.setRoomType("STANDARD");
        mappedRoom.setCapacity(2);
        mappedRoom.setPricePerDay(100.0);

        when(modelMapper.map(roomDto, Room.class)).thenReturn(mappedRoom);

        Room result = roomMapper.toEntity(roomDto);

        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("101", result.getRoomNumber());
    }

    @Test
    void shouldPreservePricePerDayWhenMappingToDTO() {
        Room room = new Room();
        room.setId("room1");
        room.setPricePerDay(250.50);

        RoomDto mappedDTO = new RoomDto();
        mappedDTO.setPricePerDay(250.50);

        when(modelMapper.map(room, RoomDto.class)).thenReturn(mappedDTO);

        RoomDto result = roomMapper.toDTO(room);

        assertNotNull(result);
        assertEquals(250.50, result.getPricePerDay());
    }

    @Test
    void shouldPreserveCapacityWhenMappingToEntity() {
        RoomDto roomDto = new RoomDto();
        roomDto.setCapacity(5);

        Room mappedRoom = new Room();
        mappedRoom.setCapacity(5);

        when(modelMapper.map(roomDto, Room.class)).thenReturn(mappedRoom);

        Room result = roomMapper.toEntity(roomDto);

        assertNotNull(result);
        assertEquals(5, result.getCapacity());
    }
}