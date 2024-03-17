package com.n11.userreviewservice.service.impl;

import com.n11.userreviewservice.client.RestaurantClient;
import com.n11.userreviewservice.common.error.GeneralErrorMessage;
import com.n11.userreviewservice.dto.restaurant.RestaurantUpdateAverageScoreRequest;
import com.n11.userreviewservice.dto.review.ReviewResponse;
import com.n11.userreviewservice.dto.review.ReviewSaveRequest;
import com.n11.userreviewservice.dto.review.ReviewUpdateRequest;
import com.n11.userreviewservice.entity.Review;
import com.n11.userreviewservice.exception.ReviewNotFoundException;
import com.n11.userreviewservice.mapper.ReviewMapper;
import com.n11.userreviewservice.repository.ReviewRepository;
import com.n11.userreviewservice.service.ReviewService;
import com.n11.userreviewservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author BeytullahBilek
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ReviewMapper reviewMapper;
    private final RestaurantClient restaurantClient;
    @Override
    public ReviewResponse save(ReviewSaveRequest request) {

        Review review=reviewMapper.convertToReview(request);
        review.setUser(userService.findEntityById(request.userId()));
        review = reviewRepository.save(review);

        List<Review> reviewList = reviewRepository.findByRestaurantId(request.restaurantId());

        double totalScore = 0.0;
        if(!reviewList.isEmpty()){
            for(Review reviewElement : reviewList){
                totalScore+=reviewElement.getRate();
            }
        }else {
            throw new RuntimeException();
        }
        Double averageScore =totalScore / reviewList.size();

        restaurantClient.updateRestaurantAverageScore(request.restaurantId(), new RestaurantUpdateAverageScoreRequest(request.restaurantId(), averageScore));
        return reviewMapper.convertToReviewResponse(review);

    }

    @Override
    public ReviewResponse getById(Long id) {
        Review review = getReviewByID(id);

        return reviewMapper.convertToReviewResponse(review);
    }

    @Override
    public List<ReviewResponse> getAll() {
        List<Review> reviewList=reviewRepository.findAll();
        return reviewMapper.convertToReviewResponseList(reviewList);
    }

    @Override
    public ReviewResponse update(Long id, ReviewUpdateRequest request) {
        Review review =getReviewByID(id);
        review.setRate(request.rate());
        review.setComment(request.comment());
        reviewRepository.save(review);
        updateAverage(id);
        review=reviewMapper.convertToUpdateToReview(review,request);
        return reviewMapper.convertToReviewResponse(review);
    }
    @Override
    public void delete(Long id) {
        String restaurantId = getReviewByID(id).getRestaurantId();
        reviewRepository.deleteById(id);
        List<Review> reviewList = reviewRepository.findByRestaurantId(restaurantId);
        double totalScore = 0.0;

        for(Review reviewElement : reviewList){
            totalScore+=reviewElement.getRate();
        }
        Double averageScore;

        if(totalScore > 0.0){
            averageScore =totalScore / reviewList.size();
        }else {
            averageScore=0.0;
        }

        restaurantClient.updateRestaurantAverageScore(restaurantId, new RestaurantUpdateAverageScoreRequest(restaurantId, averageScore));

    }
    public Review getReviewByID(Long id){
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(GeneralErrorMessage.REVIEW_NOT_FOUND));
    }

    public void updateAverage(Long id){
        Review review = getReviewByID(id);
        String restaurantId = review.getRestaurantId();

        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null for review with ID: " + id);
        }

        List<Review> reviewList = reviewRepository.findByRestaurantId(restaurantId);
        double totalScore = 0.0;

        for(Review reviewElement : reviewList){
            totalScore+=reviewElement.getRate();
        }
        Double averageScore;

        if(!reviewList.isEmpty()){
            averageScore =totalScore / reviewList.size();
            restaurantClient.updateRestaurantAverageScore(restaurantId, new RestaurantUpdateAverageScoreRequest(restaurantId, averageScore));
        }else{
            throw new ReviewNotFoundException(GeneralErrorMessage.REVIEW_NOT_FOUND);
        }
    }


}
