package com.n11.userreviewservice.service.impl;

import com.n11.userreviewservice.client.RestaurantClient;
import com.n11.userreviewservice.dto.restaurant.RestaurantUpdateAverageScoreRequest;
import com.n11.userreviewservice.dto.review.ReviewResponse;
import com.n11.userreviewservice.dto.review.ReviewSaveRequest;
import com.n11.userreviewservice.dto.review.ReviewUpdateRequest;
import com.n11.userreviewservice.entity.Review;
import com.n11.userreviewservice.entity.User;
import com.n11.userreviewservice.exception.ReviewNotFoundException;
import com.n11.userreviewservice.mapper.ReviewMapper;
import com.n11.userreviewservice.repository.ReviewRepository;
import com.n11.userreviewservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * @author BeytullahBilek
 */
@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private RestaurantClient restaurantClient;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private ReviewSaveRequest reviewSaveRequest;
    private Review review;
    private ReviewResponse reviewResponse;

    @BeforeEach
    public void setUp() {
        reviewSaveRequest = new ReviewSaveRequest(1L, "restaurantId", (byte) 5, "comment");
        review = new Review();
        review.setUser(new User());
        review.setRestaurantId("restaurantId");
        review.setRate((byte) 5);
        review.setComment("comment");
        reviewResponse = new ReviewResponse(1L,(byte) 5, "comment");
    }


    @Test
    public void shouldUpdateAverageWhenReviewListNotEmptyThenUpdateAverageScore() {
        Long reviewId = 1L;
        String restaurantId = "restaurantId";
        Review review = new Review();
        review.setId(reviewId);
        review.setRestaurantId(restaurantId);
        review.setRate((byte) 5);
        review.setComment("Great food!");

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        when(reviewRepository.findByRestaurantId(restaurantId)).thenReturn(Collections.singletonList(review));

        reviewService.updateAverage(reviewId);

        verify(restaurantClient, times(1)).updateRestaurantAverageScore(eq(restaurantId), any(RestaurantUpdateAverageScoreRequest.class));
    }

    @Test
    public void shouldUpdateAverageWhenReviewListEmptyThenNotUpdateAverageScore() {
        Long reviewId = 1L;
        String restaurantId = "restaurantId";
        Review mockReview = mock(Review.class);
        when(mockReview.getRestaurantId()).thenReturn(restaurantId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));
        when(reviewRepository.findByRestaurantId(restaurantId)).thenReturn(Collections.emptyList());
        assertThrows(ReviewNotFoundException.class, () -> reviewService.updateAverage(reviewId));

        verify(restaurantClient, never()).updateRestaurantAverageScore(anyString(), any(RestaurantUpdateAverageScoreRequest.class));
    }

    @Test
    public void shouldDeleteWhenReviewExistsThenReviewIsDeleted() {
        Long reviewId = 1L;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        reviewService.delete(reviewId);

        verify(reviewRepository, times(1)).deleteById(reviewId);
        verify(restaurantClient, times(1)).updateRestaurantAverageScore(anyString(), any(RestaurantUpdateAverageScoreRequest.class));
    }

    @Test
    public void shouldDeleteWhenReviewDoesNotExistThenReviewNotFoundExceptionIsThrown() {
        Long reviewId = 1L;
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> reviewService.delete(reviewId));

        verify(reviewRepository, times(1)).findById(reviewId);
    }

    @Test
    public void shouldGetAllWhenReviewsExistThenReturnReviewResponseList() {
        when(reviewRepository.findAll()).thenReturn(Collections.singletonList(review));
        when(reviewMapper.convertToReviewResponseList(Collections.singletonList(review))).thenReturn(Collections.singletonList(reviewResponse));

        List<ReviewResponse> result = reviewService.getAll();

        verify(reviewRepository, times(1)).findAll();
        verify(reviewMapper, times(1)).convertToReviewResponseList(Collections.singletonList(review));
        assertEquals(Collections.singletonList(reviewResponse), result);
    }

    @Test
    public void shouldGetAllWhenNoReviewsThenReturnEmptyList() {
        when(reviewRepository.findAll()).thenReturn(Collections.emptyList());

        List<ReviewResponse> result = reviewService.getAll();

        verify(reviewRepository, times(1)).findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldGetByIdWhenReviewExistsThenReturnReviewResponse() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        when(reviewMapper.convertToReviewResponse(review)).thenReturn(reviewResponse);

        ReviewResponse result = reviewService.getById(1L);

        verify(reviewRepository, times(1)).findById(anyLong());
        verify(reviewMapper, times(1)).convertToReviewResponse(review);
        assertEquals(reviewResponse, result);
    }

    @Test
    public void shouldGetByIdWhenReviewDoesNotExistThenThrowReviewNotFoundException() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> reviewService.getById(1L));

        verify(reviewRepository, times(1)).findById(anyLong());
    }

    @Test
    public void shouldSaveWhenAllDependenciesAreMockedAndValidDataThenSaveReviewAndCallRestaurantClient() {
        when(reviewMapper.convertToReview(reviewSaveRequest)).thenReturn(review);
        when(userService.findEntityById(reviewSaveRequest.userId())).thenReturn(new User());
        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewRepository.findByRestaurantId(reviewSaveRequest.restaurantId())).thenReturn(Collections.singletonList(review));
        when(reviewMapper.convertToReviewResponse(review)).thenReturn(reviewResponse);

        ReviewResponse result = reviewService.save(reviewSaveRequest);

        verify(reviewRepository, times(1)).save(review);
        verify(restaurantClient, times(1)).updateRestaurantAverageScore(anyString(), any(RestaurantUpdateAverageScoreRequest.class));
        assertEquals(reviewResponse, result);
    }

    @Test
    public void shouldSaveWhenReviewRepositoryThrowsExceptionThenDoNotSaveReviewAndDoNotCallRestaurantClient() {
        when(reviewMapper.convertToReview(reviewSaveRequest)).thenReturn(review);
        when(userService.findEntityById(reviewSaveRequest.userId())).thenReturn(new User());
        when(reviewRepository.save(review)).thenThrow(new RuntimeException());

        try {
            reviewService.save(reviewSaveRequest);
        } catch (RuntimeException e) {
            verify(reviewRepository, times(1)).save(review);
            verifyNoInteractions(restaurantClient);
        }
    }

    @Test
    public void shouldSaveWhenReviewRepositoryReturnsReviewThenSaveReviewAndCallRestaurantClient() {
        when(reviewMapper.convertToReview(reviewSaveRequest)).thenReturn(review);
        when(userService.findEntityById(reviewSaveRequest.userId())).thenReturn(new User());
        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewRepository.findByRestaurantId(reviewSaveRequest.restaurantId())).thenReturn(Collections.singletonList(review));
        when(reviewMapper.convertToReviewResponse(review)).thenReturn(reviewResponse);

        ReviewResponse result = reviewService.save(reviewSaveRequest);

        verify(reviewRepository, times(1)).save(review);
        verify(restaurantClient, times(1)).updateRestaurantAverageScore(anyString(), any(RestaurantUpdateAverageScoreRequest.class));
        assertEquals(reviewResponse, result);
    }

    @Test
    public void shouldUpdateWhenReviewExistsThenUpdateReviewAndReturnReviewResponse() {
        Long reviewId = 1L;
        String restaurantId = "restaurantId";
        ReviewUpdateRequest updateRequest = new ReviewUpdateRequest((byte) 4, "Updated comment");
        Review originalReview = new Review();
        originalReview.setId(reviewId);
        originalReview.setRestaurantId(restaurantId);
        originalReview.setRate((byte) 5);
        originalReview.setComment("Original comment");

        Review updatedReview = new Review();
        updatedReview.setId(reviewId);
        updatedReview.setRestaurantId(restaurantId);
        updatedReview.setRate(updateRequest.rate());
        updatedReview.setComment(updateRequest.comment());

        ReviewResponse updatedReviewResponse = new ReviewResponse(1L,updateRequest.rate(), updateRequest.comment());

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(originalReview));
        when(reviewMapper.convertToUpdateToReview(any(Review.class), eq(updateRequest))).thenReturn(updatedReview);
        when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);
        when(reviewMapper.convertToReviewResponse(updatedReview)).thenReturn(updatedReviewResponse);

        when(reviewRepository.findByRestaurantId(restaurantId)).thenReturn(List.of(updatedReview));

        ReviewResponse result = reviewService.update(reviewId, updateRequest);

        verify(reviewRepository).save(any(Review.class));
        verify(reviewMapper).convertToReviewResponse(updatedReview);
        verify(restaurantClient).updateRestaurantAverageScore(eq(restaurantId), any(RestaurantUpdateAverageScoreRequest.class)); // eq(restaurantId) kullanarak doğru restaurantId ile çağrıldığını kontrol edin
        assertEquals(updatedReviewResponse, result);
    }

    @Test
    public void shouldUpdateWhenReviewDoesNotExistThenThrowReviewNotFoundException() {
        Long reviewId = 1L;
        ReviewUpdateRequest updateRequest = new ReviewUpdateRequest((byte) 4, "Updated comment");

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> reviewService.update(reviewId, updateRequest));

        verify(reviewRepository, times(1)).findById(reviewId);
    }

    @Test
    public void shouldUpdateWhenReviewRepositoryThrowsExceptionThenDoNotUpdateReviewAndDoNotCallRestaurantClient() {
        Long reviewId = 1L;
        ReviewUpdateRequest updateRequest = new ReviewUpdateRequest((byte) 4, "Updated comment");
        Review originalReview = new Review();
        originalReview.setId(reviewId);
        originalReview.setRate((byte) 5);
        originalReview.setComment("Original comment");

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(originalReview));

        when(reviewRepository.save(any(Review.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> reviewService.update(reviewId, updateRequest));

        verify(reviewRepository).save(any(Review.class));

        verifyNoInteractions(restaurantClient);
    }

}