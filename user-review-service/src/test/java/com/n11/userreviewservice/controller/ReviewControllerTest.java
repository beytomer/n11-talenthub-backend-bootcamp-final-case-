package com.n11.userreviewservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n11.userreviewservice.dto.review.ReviewResponse;
import com.n11.userreviewservice.dto.review.ReviewSaveRequest;
import com.n11.userreviewservice.dto.review.ReviewUpdateRequest;
import com.n11.userreviewservice.service.KafkaProducerService;
import com.n11.userreviewservice.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @author BeytullahBilek
 */
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private KafkaProducerService kafkaProducerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        Mockito.doNothing().when(kafkaProducerService).sendMessage(any(String.class), any(String.class));
    }

    @Test
    void shouldWhenSaveReview_thenReturns201AndSendsKafkaMessage() throws Exception {
        ReviewSaveRequest request = new ReviewSaveRequest(1L, "restaurant123", (byte) 5, "Great food!");
        ReviewResponse response = new ReviewResponse(1L,(byte) 5, "Great food!");

        Mockito.when(reviewService.save(any(ReviewSaveRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Mockito.verify(kafkaProducerService,Mockito.times(2)).sendMessage(eq("ReviewControllerLog"), any(String.class));
    }

    @Test
    void shouldWhenGetReviewById_thenReturns200() throws Exception {
        ReviewResponse response = new ReviewResponse(1L,(byte) 5, "Delicious!");

        Mockito.when(reviewService.getById(any(Long.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/reviews/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldWhenGetAllReviews_thenReturns200() throws Exception {
        ReviewResponse response1 = new ReviewResponse(1L,(byte) 5, "Delicious!");
        ReviewResponse response2 = new ReviewResponse(1L,(byte) 4, "Good!");

        Mockito.when(reviewService.getAll()).thenReturn(Arrays.asList(response1, response2));

        mockMvc.perform(get("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldWhenUpdateReview_thenReturns200AndSendsKafkaMessage() throws Exception {
        ReviewUpdateRequest request = new ReviewUpdateRequest((byte) 4, "Good, but could be better.");
        ReviewResponse response = new ReviewResponse(1L,(byte) 4, "Good, but could be better.");

        Mockito.when(reviewService.update(eq(1L), any(ReviewUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(patch("/api/v1/reviews/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(kafkaProducerService,Mockito.times(2)).sendMessage(eq("ReviewControllerLog"), any(String.class));
    }

    @Test
    void shouldWhenDeleteReview_thenReturns200AndSendsKafkaMessage() throws Exception {
        Mockito.doNothing().when(reviewService).delete(any(Long.class));

        mockMvc.perform(delete("/api/v1/reviews/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(kafkaProducerService,Mockito.times(2)).sendMessage(eq("ReviewControllerLog"), any(String.class));
    }
}