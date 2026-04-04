package com.HMA.Controller;

import com.HMA.DTO.RoomDto;
import com.HMA.Entity.Room;
import com.HMA.Exception.ResourceNotFoundException;
import com.HMA.Service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSaveRoomWhenValidDataProvided() throws Exception {
        RoomDto roomDto = new RoomDto();
        roomDto.setRoomNumber("101");
        roomDto.setRoomType("STANDARD");
        roomDto.setCapacity(2);
        roomDto.setPricePerDay(100.0);

        RoomDto savedRoom = new RoomDto();
        savedRoom.setId("room1");
        savedRoom.setRoomNumber("101");
        savedRoom.setRoomType("STANDARD");
        savedRoom.setCapacity(2);
        savedRoom.setPricePerDay(100.0);

        when(roomService.saveRoom(any(RoomDto.class))).thenReturn(savedRoom);

        mockMvc.perform(post("/api/v1/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("room1"))
                .andExpect(jsonPath("$.roomNumber").value("101"));
    }

    @Test
    void shouldUpdateRoomWhenValidDataProvided() throws Exception {
        Room room = new Room();
        room.setRoomNumber("102");
        room.setRoomType("DELUXE");
        room.setCapacity(4);
        room.setPricePerDay(150.0);
        Room updatedRoom = new Room();
        updatedRoom.setId("room1");
        updatedRoom.setRoomNumber("102");
        updatedRoom.setRoomType("DELUXE");
        updatedRoom.setCapacity(4);
        updatedRoom.setPricePerDay(150.0);
        when(roomService.updateRoom(eq(1), any(Room.class))).thenReturn(updatedRoom);
        mockMvc.perform(put("/api/v1/room/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room))).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("room1"))
                .andExpect(jsonPath("$.roomType").value("DELUXE"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonexistentRoom() throws Exception {
        Room room = new Room();
        room.setRoomNumber("102");

        when(roomService.updateRoom(eq(999), any(Room.class))).thenThrow(new ResourceNotFoundException("Room not found"));

        mockMvc.perform(put("/api/v1/room/999").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room))).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldReturnAllRooms() throws Exception {
        Room room1 = new Room();
        room1.setId("room1");
        room1.setRoomNumber("101");
        room1.setRoomType("STANDARD");
        room1.setCapacity(2);
        room1.setPricePerDay(100.0);
        Room room2 = new Room();
        room2.setId("room2");
        room2.setRoomNumber("102");
        room2.setRoomType("SUITE");
        room2.setCapacity(4);
        room2.setPricePerDay(200.0);
        List<Room> rooms = Arrays.asList(room1, room2);
        when(roomService.getAllRooms()).thenReturn(rooms);
        mockMvc.perform(get("/api/v1/room"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].roomNumber").value("101"))
                .andExpect(jsonPath("$[1].roomNumber").value("102"));
    }

    @Test
    void shouldReturnRoomWhenIdExists() throws Exception {
        Room room = new Room();
        room.setId("room1");
        room.setRoomNumber("101");
        room.setRoomType("STANDARD");
        room.setCapacity(2);
        room.setPricePerDay(100.0);
        when(roomService.getRoomById(1)).thenReturn(room);
        mockMvc.perform(get("/api/v1/room/1"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value("room1"))
                .andExpect(jsonPath("$.capacity").value(2));
    }

    @Test
    void shouldReturnNotFoundWhenRoomIdDoesNotExist() throws Exception {
        when(roomService.getRoomById(999)).thenThrow(new ResourceNotFoundException("Room not found"));
        mockMvc.perform(get("/api/v1/room/999"))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldDeleteRoomWhenIdExists() throws Exception {
        when(roomService.deleteRoom(1)).thenReturn("Room deleted successfully");
        mockMvc.perform(delete("/api/v1/room/1"))
                .andExpect(status().isOk()).andExpect(content().string("Room deleted successfully"));
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonexistentRoom() throws Exception {
        when(roomService.deleteRoom(999)).thenThrow(new ResourceNotFoundException("Room not found"));
        mockMvc.perform(delete("/api/v1/room/999"))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.status").value(404));
    }
}