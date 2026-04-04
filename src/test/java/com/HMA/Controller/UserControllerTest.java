package com.HMA.Controller;

import com.HMA.DTO.UserDto;
import com.HMA.Entity.User;
import com.HMA.Exception.ResourceNotFoundException;
import com.HMA.Service.UserService;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUserWhenValidDataProvided() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setEmail("john@example.com");
        userDto.setPassword("Password1!");

        UserDto createdUser = new UserDto();
        createdUser.setId("user1");
        createdUser.setName("John Doe");
        createdUser.setEmail("john@example.com");
        createdUser.setPassword("Password1!");

        when(userService.createUser(any(UserDto.class))).thenReturn(createdUser);

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("user1"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        User user1 = new User();
        user1.setId("user1");
        user1.setName("John Doe");
        user1.setEmail("john@example.com");
        user1.setPassword("Password1!");

        User user2 = new User();
        user2.setId("user2");
        user2.setName("Jane Doe");
        user2.setEmail("jane@example.com");
        user2.setPassword("Password2!");

        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("user1"))
                .andExpect(jsonPath("$[1].id").value("user2"));
    }

    @Test
    void shouldReturnUserWhenIdExists() throws Exception {
        User user = new User();
        user.setId("user1");
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("Password1!");

        when(userService.getUserById("user1")).thenReturn(user);

        mockMvc.perform(get("/api/v1/user/user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("user1"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void shouldReturnNotFoundWhenUserIdDoesNotExist() throws Exception {
        when(userService.getUserById("nonexistent")).thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(get("/api/v1/user/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldUpdateUserWhenIdExists() throws Exception {
        User userUpdate = new User();
        userUpdate.setName("John Updated");
        userUpdate.setEmail("john.updated@example.com");
        userUpdate.setPassword("NewPassword1!");

        User updatedUser = new User();
        updatedUser.setId("user1");
        updatedUser.setName("John Updated");
        updatedUser.setEmail("john.updated@example.com");
        updatedUser.setPassword("NewPassword1!");

        when(userService.updateUser(eq("user1"), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/user/user1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("user1"))
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentUser() throws Exception {
        User userUpdate = new User();
        userUpdate.setName("John Updated");
        userUpdate.setEmail("john.updated@example.com");
        userUpdate.setPassword("NewPassword1!");

        when(userService.updateUser(eq("nonexistent"), any(User.class))).thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(put("/api/v1/user/nonexistent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldDeleteUserWhenIdExists() throws Exception {
        doNothing().when(userService).deleteUser("user1");

        mockMvc.perform(delete("/api/v1/user/user1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully!"));
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentUser() throws Exception {
        doThrow(new ResourceNotFoundException("User not found")).when(userService).deleteUser("nonexistent");

        mockMvc.perform(delete("/api/v1/user/nonexistent"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }
}