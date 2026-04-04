package com.HMA.Service;

import com.HMA.DTO.RoomDto;
import com.HMA.Entity.Room;
import com.HMA.Mapper.RoomMapper;
import com.HMA.Repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RoomServiceImplTest {

    @Autowired
    private RoomServiceImpl roomService;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private RoomMapper roomMapper;

    @Test
    void shouldSaveRoomWhenValidDataProvided() {
        RoomDto roomDto = new RoomDto();
        roomDto.setRoomNumber("101");
        roomDto.setRoomType("STANDARD");
        roomDto.setCapacity(2);
        roomDto.setPricePerDay(100.0);

        Room room = new Room();
        room.setRoomNumber("101");
        room.setRoomType("STANDARD");
        room.setCapacity(2);
        room.setPricePerDay(100.0);

        Room savedRoom = new Room();
        savedRoom.setId("room1");
        savedRoom.setRoomNumber("101");
        savedRoom.setRoomType("STANDARD");
        savedRoom.setCapacity(2);
        savedRoom.setPricePerDay(100.0);

        RoomDto expectedDto = new RoomDto();
        expectedDto.setId("room1");
        expectedDto.setRoomNumber("101");
        expectedDto.setRoomType("STANDARD");
        expectedDto.setCapacity(2);
        expectedDto.setPricePerDay(100.0);

        when(roomMapper.toEntity(roomDto)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(savedRoom);
        when(roomMapper.toDTO(savedRoom)).thenReturn(expectedDto);

        RoomDto result = roomService.saveRoom(roomDto);

        assertNotNull(result);
        assertEquals("room1", result.getId());
        assertEquals("101", result.getRoomNumber());
    }

    @Test
    void shouldReturnAllRooms() {
        Room room1 = new Room();
        room1.setId("room1");
        room1.setRoomNumber("101");
        room1.setRoomType("STANDARD");
        Room room2 = new Room();
        room2.setId("room2");
        room2.setRoomNumber("102");
        room2.setRoomType("DELUXE");
        List<Room> rooms = Arrays.asList(room1, room2);
        when(roomRepository.findAll()).thenReturn(rooms);
        List<Room> result = roomService.getAllRooms();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("room1", result.get(0).getId());
    }

    @Test
    void shouldReturnEmptyListWhenNoRoomsExist() {
        when(roomRepository.findAll()).thenReturn(Arrays.asList());

        List<Room> result = roomService.getAllRooms();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnRoomWhenIdExists() {
        Room room = new Room();
        room.setId("1");
        room.setRoomNumber("101");
        room.setRoomType("STANDARD");

        when(roomRepository.findById("1")).thenReturn(Optional.of(room));

        Room result = roomService.getRoomById(1);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("101", result.getRoomNumber());
    }

    @Test
    void shouldThrowRuntimeExceptionWhenRoomIdNotFound() {
        when(roomRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roomService.getRoomById(999));
    }

    @Test
    void shouldUpdateRoomWhenIdExists() {
        Room existingRoom = new Room();
        existingRoom.setId("1");
        existingRoom.setRoomNumber("101");
        existingRoom.setRoomType("STANDARD");
        existingRoom.setCapacity(2);
        existingRoom.setPricePerDay(100.0);
        Room updatedData = new Room();
        updatedData.setRoomNumber("101-A");
        updatedData.setRoomType("DELUXE");
        updatedData.setCapacity(4);
        updatedData.setPricePerDay(150.0);
        Room expectedResult = new Room();
        expectedResult.setId("1");
        expectedResult.setRoomNumber("101-A");
        expectedResult.setRoomType("DELUXE");
        expectedResult.setCapacity(4);
        expectedResult.setPricePerDay(150.0);
        when(roomRepository.findById("1")).thenReturn(Optional.of(existingRoom));
        when(roomRepository.save(any(Room.class))).thenReturn(expectedResult);
        Room result = roomService.updateRoom(1, updatedData);
        assertNotNull(result);
        assertEquals("101-A", result.getRoomNumber());
        assertEquals("DELUXE", result.getRoomType());
        assertEquals(4, result.getCapacity());
    }

    @Test
    void shouldThrowRuntimeExceptionWhenUpdatingNonExistentRoom() {
        Room updatedData = new Room();
        updatedData.setRoomNumber("101-A");
        when(roomRepository.findById("999")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> roomService.updateRoom(999, updatedData));
    }

    @Test
    void shouldDeleteRoomAndReturnSuccessMessage() {
        String result = roomService.deleteRoom(1);
        assertEquals("Room deleted successfully", result);
        verify(roomRepository).deleteById("1");
    }

    @Test
    void shouldReturnSuccessMessageWhenDeletingNonExistentRoom() {
        String result = roomService.deleteRoom(999);

        assertEquals("Room deleted successfully", result);
        verify(roomRepository).deleteById("999");
    }
}