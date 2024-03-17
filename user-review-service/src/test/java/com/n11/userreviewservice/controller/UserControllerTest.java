package com.n11.userreviewservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.n11.userreviewservice.dto.user.UserSaveRequest;
import com.n11.userreviewservice.dto.user.UserUpdateRequest;
import com.n11.userreviewservice.entity.enums.Gender;
import com.n11.userreviewservice.service.KafkaProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    /**
     * @author BeytullahBilek
     */
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaProducerService kafkaProducerService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Mockito.doNothing().when(kafkaProducerService).sendMessage(anyString(), anyString());
    }

    @Test
    void shouldWhenSaveUser_thenReturns201AndSendsKafkaMessage() throws Exception {
        UserSaveRequest request = new UserSaveRequest("John", "Doe", LocalDateTime.now().minusYears(20), "john.doe@example.com", Gender.MALE);
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Mockito.verify(kafkaProducerService, Mockito.times(2)).sendMessage(anyString(), anyString());
    }

    @Test
    void shouldWhenGetUserById_thenReturns200() throws Exception {
        Long userId = 1L;
        mockMvc.perform(get("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldWhenGetAllUsers_thenReturns200() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldWhenUpdateUser_thenReturns200AndSendsKafkaMessage() throws Exception {
        Long userId = 1L;
        UserUpdateRequest request = new UserUpdateRequest("Jane", "Doe", LocalDateTime.now().minusYears(20), "jane.doe@example.com", Gender.FEMALE);
        mockMvc.perform(patch("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(kafkaProducerService, Mockito.times(2)).sendMessage(anyString(), anyString());
    }

    @Test
    void shouldWhenDeleteUser_thenReturns200AndSendsKafkaMessage() throws Exception {
        Long userId = 1L;
        mockMvc.perform(delete("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(kafkaProducerService, Mockito.times(2)).sendMessage(anyString(), anyString());
    }

    @Test
    void shouldWhenDeactivateUser_thenReturns200AndSendsKafkaMessage() throws Exception {
        Long userId = 1L;
        mockMvc.perform(patch("/api/v1/users/deactivate/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(kafkaProducerService, Mockito.times(2)).sendMessage(anyString(), anyString());
    }
}