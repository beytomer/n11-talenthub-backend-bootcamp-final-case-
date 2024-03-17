package com.n11.userreviewservice.service.impl;

import com.n11.userreviewservice.dto.user.UserResponse;
import com.n11.userreviewservice.dto.user.UserSaveRequest;
import com.n11.userreviewservice.dto.user.UserUpdateRequest;
import com.n11.userreviewservice.entity.User;
import com.n11.userreviewservice.entity.enums.Gender;
import com.n11.userreviewservice.entity.enums.Status;
import com.n11.userreviewservice.exception.UserNotFoundException;
import com.n11.userreviewservice.mapper.UserMapper;
import com.n11.userreviewservice.repository.UserRepository;
import com.n11.userreviewservice.service.KafkaProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * @author BeytullahBilek
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private KafkaProducerService producerService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserResponse userResponse;
    private UserSaveRequest userSaveRequest;
    private UserUpdateRequest userUpdateRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        userResponse = new UserResponse(1L,"TestName", "TestSurname", LocalDateTime.now(), "TestName.doe@example.com", Gender.MALE);
        userSaveRequest = new UserSaveRequest("TestName", "TestSurname", LocalDateTime.of(1990, 1, 1,12,30), "TestName@example.com", Gender.MALE);
        userUpdateRequest = new UserUpdateRequest("TestName", "TestSurname", LocalDateTime.of(1990, 1, 1,12,30), "TestName@example.com", Gender.MALE);
    }

    @Test
    void shouldSaveWhenUserSavedThenReturnUserResponse() {
        when(userMapper.convertToUser(userSaveRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.convertToUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.save(userSaveRequest);

        verify(userRepository).save(user);
        assertNotNull(result);
    }

    @Test
    void shouldGetByIdWhenUserFoundThenReturnUserResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.convertToUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.getById(1L);

        verify(userRepository).findById(1L);
        assertNotNull(result);
    }

    @Test
    void shouldGetAllWhenUsersExistThenReturnUserResponseList() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userMapper.convertToUserResponseList(Collections.singletonList(user))).thenReturn(Collections.singletonList(userResponse));

        List<UserResponse> result = userService.getAll();

        verify(userRepository).findAll();
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldUpdateWhenUserUpdatedThenReturnUserResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.convertUpdateToUser(user, userUpdateRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.convertToUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.update(1L, userUpdateRequest);

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
        assertNotNull(result);
    }

    @Test
    void shouldDeleteWhenUserDeletedThenNoReturn() {
        doNothing().when(userRepository).deleteById(1L);

        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void shouldActivateWhenUserActivatedThenReturnUserResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.convertToUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.activate(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
        assertEquals(Status.ACTIVE, user.getStatus());
        assertNotNull(result);
    }

    @Test
    void shouldFindEntityByIdWhenUserFoundThenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findEntityById(1L);

        verify(userRepository).findById(1L);
        assertNotNull(result);
    }

    @Test
    void shouldDeactivateWhenUserDeactivatedThenReturnUserResponse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.convertToUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.deactivate(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
        assertEquals(Status.INACTIVE, user.getStatus());
        assertNotNull(result);
    }

    @Test
    void shouldGetByIdWhenUserNotFoundThenThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getById(1L));

        verify(userRepository).findById(1L);
    }
}