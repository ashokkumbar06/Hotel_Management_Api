package com.HMA.Mapper;

import com.HMA.DTO.UserDto;
import com.HMA.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserMappingTest {

    @Autowired
    private UserMapping userMapping;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void shouldMapUserToDTOWithAllFields() {
        User user = new User();
        user.setId("user1");
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("Password1!");

        UserDto mappedDTO = new UserDto();
        mappedDTO.setId("user1");
        mappedDTO.setName("John Doe");
        mappedDTO.setEmail("john@example.com");
        mappedDTO.setPassword("Password1!");

        when(modelMapper.map(user, UserDto.class)).thenReturn(mappedDTO);

        UserDto result = userMapping.toDTO(user);

        assertNotNull(result);
        assertEquals("user1", result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("Password1!", result.getPassword());
    }

    @Test
    void shouldMapUserToDTOWithNullEmail() {
        User user = new User();
        user.setId("user1");
        user.setName("Jane Doe");
        user.setEmail(null);
        user.setPassword("Password2!");
        UserDto mappedDTO = new UserDto();
        mappedDTO.setId("user1");
        mappedDTO.setName("Jane Doe");
        mappedDTO.setEmail(null);
        mappedDTO.setPassword("Password2!");
        when(modelMapper.map(user, UserDto.class)).thenReturn(mappedDTO);
        UserDto result = userMapping.toDTO(user);
        assertNotNull(result);
        assertNull(result.getEmail());
        assertEquals("Jane Doe", result.getName());
    }

    @Test
    void shouldMapUserToDTOWithNullPassword() {
        User user = new User();
        user.setId("user1");
        user.setName("Bob Smith");
        user.setEmail("bob@example.com");
        user.setPassword(null);
        UserDto mappedDTO = new UserDto();
        mappedDTO.setId("user1");
        mappedDTO.setName("Bob Smith");
        mappedDTO.setEmail("bob@example.com");
        mappedDTO.setPassword(null);
        when(modelMapper.map(user, UserDto.class)).thenReturn(mappedDTO);
        UserDto result = userMapping.toDTO(user);
        assertNotNull(result);
        assertNull(result.getPassword());
        assertEquals("bob@example.com", result.getEmail());
    }

    @Test
    void shouldMapUserToDTOWithNullName() {
        User user = new User();
        user.setId("user1");
        user.setName(null);
        user.setEmail("test@example.com");
        user.setPassword("Password1!");
        UserDto mappedDTO = new UserDto();
        mappedDTO.setId("user1");
        mappedDTO.setName(null);
        mappedDTO.setEmail("test@example.com");
        mappedDTO.setPassword("Password1!");
        when(modelMapper.map(user, UserDto.class)).thenReturn(mappedDTO);
        UserDto result = userMapping.toDTO(user);
        assertNotNull(result);
        assertNull(result.getName());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void shouldMapDTOToEntityWithAllFields() {
        UserDto userDto = new UserDto();
        userDto.setId("user1");
        userDto.setName("John Doe");
        userDto.setEmail("john@example.com");
        userDto.setPassword("Password1!");
        User mappedUser = new User();
        mappedUser.setId("user1");
        mappedUser.setName("John Doe");
        mappedUser.setEmail("john@example.com");
        mappedUser.setPassword("Password1!");

        when(modelMapper.map(userDto, User.class)).thenReturn(mappedUser);

        User result = userMapping.toEntity(userDto);

        assertNotNull(result);
        assertEquals("user1", result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("Password1!", result.getPassword());
    }

    @Test
    void shouldMapDTOToEntityWithNullId() {
        UserDto userDto = new UserDto();
        userDto.setId(null);
        userDto.setName("Jane Doe");
        userDto.setEmail("jane@example.com");
        userDto.setPassword("Password2!");
        User mappedUser = new User();
        mappedUser.setId(null);
        mappedUser.setName("Jane Doe");
        mappedUser.setEmail("jane@example.com");
        mappedUser.setPassword("Password2!");
        when(modelMapper.map(userDto, User.class)).thenReturn(mappedUser);
        User result = userMapping.toEntity(userDto);
        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Jane Doe", result.getName());
    }

    @Test
    void shouldMapDTOToEntityWithNullEmail() {
        UserDto userDto = new UserDto();
        userDto.setId("user1");
        userDto.setName("Alice Johnson");
        userDto.setEmail(null);
        userDto.setPassword("Password3!");
        User mappedUser = new User();
        mappedUser.setId("user1");
        mappedUser.setName("Alice Johnson");
        mappedUser.setEmail(null);
        mappedUser.setPassword("Password3!");
        when(modelMapper.map(userDto, User.class)).thenReturn(mappedUser);
        User result = userMapping.toEntity(userDto);
        assertNotNull(result);
        assertNull(result.getEmail());
        assertEquals("Alice Johnson", result.getName());
    }

    @Test
    void shouldPreserveIdWhenMappingToDTO() {
        User user = new User();
        user.setId("unique-user-id");
        UserDto mappedDTO = new UserDto();
        mappedDTO.setId("unique-user-id");
        when(modelMapper.map(user, UserDto.class)).thenReturn(mappedDTO);
        UserDto result = userMapping.toDTO(user);
        assertNotNull(result);
        assertEquals("unique-user-id", result.getId());
    }

    @Test
    void shouldPreserveNameWhenMappingToEntity() {
        UserDto userDto = new UserDto();
        userDto.setName("Complete Name");
        User mappedUser = new User();
        mappedUser.setName("Complete Name");
        when(modelMapper.map(userDto, User.class)).thenReturn(mappedUser);
        User result = userMapping.toEntity(userDto);
        assertNotNull(result);
        assertEquals("Complete Name", result.getName());
    }

    @Test
    void shouldPreserveEmailWhenMappingToDTO() {
        User user = new User();
        user.setId("user1");
        user.setEmail("test@example.com");
        UserDto mappedDTO = new UserDto();
        mappedDTO.setId("user1");
        mappedDTO.setEmail("test@example.com");
        when(modelMapper.map(user, UserDto.class)).thenReturn(mappedDTO);
        UserDto result = userMapping.toDTO(user);
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }
}