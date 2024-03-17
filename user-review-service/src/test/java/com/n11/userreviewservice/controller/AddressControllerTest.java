package com.n11.userreviewservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n11.userreviewservice.UserReviewServiceApplication;
import com.n11.userreviewservice.dto.adress.AddressSaveRequest;
import com.n11.userreviewservice.dto.adress.AddressUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @author BeytullahBilek
 */
@SpringBootTest(classes = UserReviewServiceApplication.class)
@AutoConfigureMockMvc
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private final Long existingUserId = 1L;

    @Test
    public void shouldSaveAddress() throws Exception {
        AddressSaveRequest request = new AddressSaveRequest("Ankara", "Çankaya", "Bir yer", 1L);

        mockMvc.perform(post("/api/v1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.city").value("Ankara"))
                .andExpect(jsonPath("$.data.county").value("Çankaya"));
    }

    @Test
    public void shouldUpdateAddress() throws Exception {
        Long addressId = 1L;
        AddressUpdateRequest request = new AddressUpdateRequest("İstanbul", "Beşiktaş", "Başka bir yer");

        mockMvc.perform(patch("/api/v1/addresses/{id}", addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.city").value("İstanbul"))
                .andExpect(jsonPath("$.data.county").value("Beşiktaş"));
    }

    @Test
    public void shouldDeleteAddress() throws Exception {
        Long addressId = 2L;

        mockMvc.perform(delete("/api/v1/addresses/{id}", addressId))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetByIdWhenValidUserIdThenReturnAddresses() throws Exception {
        Long userId = 1L;

        mockMvc.perform(get("/api/v1/addresses/with-userId/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void shouldGetByIdWhenInvalidUserIdThenReturnBadRequest() throws Exception {
        Long userId = -1L;

        mockMvc.perform(get("/api/v1/addresses/with-userId/{userId}", userId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldWhenGetAddressByUserId_thenReturnsAddresses() throws Exception {
        mockMvc.perform(get("/api/v1/addresses/with-userId/{userId}", existingUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].city").isNotEmpty())
                .andExpect(jsonPath("$.data[0].county").isNotEmpty())
                .andExpect(jsonPath("$.data[0].location").isNotEmpty());
    }
    @Test
    public void shouldGetAllWhenCalledThenReturnAddresses() throws Exception {
        mockMvc.perform(get("/api/v1/addresses")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[*].city").isNotEmpty())
                .andExpect(jsonPath("$.data[*].county").isNotEmpty())
                .andExpect(jsonPath("$.data[*].location").isNotEmpty());
    }
    private final Long addressIdToDelete = 1L;

    @Test
    public void shouldDeleteAddress_thenAddressIsDeleted() throws Exception {
        mockMvc.perform(delete("/api/v1/addresses/{id}", addressIdToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
