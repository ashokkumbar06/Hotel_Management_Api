package com.HMA.Service;

import com.HMA.DTO.UserDto;
import com.HMA.Entity.User;
import com.HMA.Mapper.UserMapping;
import com.HMA.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapping userMapping;

    @Test
    void shouldCreateUserWithValidData() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setEmail("john@example.com");
        userDto.setPassword("Password1!");

        User userEntity = new User();
        userEntity.setName("John Doe");
        userEntity.setEmail("john@example.com");
        userEntity.setPassword("Password1!");

        User savedUser = new User();
        savedUser.setId("user1");
        savedUser.setName("John Doe");
        savedUser.setEmail("john@example.com");
        savedUser.setPassword("Password1!");

        UserDto expectedDto = new UserDto();
        expectedDto.setId("user1");
        expectedDto.setName("John Doe");
        expectedDto.setEmail("john@example.com");
        expectedDto.setPassword("Password1!");

        when(userMapping.toEntity(userDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedUser);
        when(userMapping.toDTO(savedUser)).thenReturn(expectedDto);

        UserDto result = userService.createUser(userDto);

        assertNotNull(result);
        assertEquals("user1", result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void shouldReturnAllUsers() {
        User user1 = new User();
        user1.setId("user1");
        user1.setName("John Doe");
        user1.setEmail("john@example.com");

        User user2 = new User();
        user2.setId("user2");
        user2.setName("Jane Doe");
        user2.setEmail("jane@example.com");

        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getId());
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnUserWhenIdExists() {
        User user = new User();
        user.setId("user1");
        user.setName("John Doe");
        user.setEmail("john@example.com");

        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        User result = userService.getUserById("user1");

        assertNotNull(result);
        assertEquals("user1", result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void shouldReturnNullWhenUserIdDoesNotExist() {
        when(userRepository.findById("nonexistent")).thenReturn(Optional.empty());

        User result = userService.getUserById("nonexistent");

        assertNull(result);
    }

    @Test
    void shouldUpdateUserWhenIdExists() {
        User existingUser = new User();
        existingUser.setId("user1");
        existingUser.setName("John Doe");
        existingUser.setEmail("john@example.com");
        existingUser.setPassword("OldPassword1!");

        User updatedData = new User();
        updatedData.setName("John Updated");
        updatedData.setEmail("john.updated@example.com");
        updatedData.setPassword("NewPassword1!");

        User expectedResult = new User();
        expectedResult.setId("user1");
        expectedResult.setName("John Updated");
        expectedResult.setEmail("john.updated@example.com");
        expectedResult.setPassword("NewPassword1!");

        when(userRepository.findById("user1")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(expectedResult);

        User result = userService.updateUser("user1", updatedData);

        assertNotNull(result);
        assertEquals("John Updated", result.getName());
        assertEquals("john.updated@example.com", result.getEmail());
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistentUser() {
        User updatedData = new User();
        updatedData.setName("John Updated");

        when(userRepository.findById("nonexistent")).thenReturn(Optional.empty());

        User result = userService.updateUser("nonexistent", updatedData);

        assertNull(result);
    }

    @Test
    void shouldDeleteUserWhenIdExists() {
        doNothing().when(userRepository).deleteById("user1");

        userService.deleteUser("user1");

        verify(userRepository).deleteById("user1");
    }
}