package com.n11.userreviewservice.service;

import com.n11.userreviewservice.dto.review.ReviewResponse;
import com.n11.userreviewservice.dto.review.ReviewSaveRequest;
import com.n11.userreviewservice.dto.review.ReviewUpdateRequest;

import java.util.List;
/**
 * @author BeytullahBilek
 */
public interface ReviewService {
    ReviewResponse save(ReviewSaveRequest request);

    ReviewResponse getById(Long id);

    List<ReviewResponse> getAll();

    ReviewResponse update(Long id, ReviewUpdateRequest request);

    void delete(Long id);

}
