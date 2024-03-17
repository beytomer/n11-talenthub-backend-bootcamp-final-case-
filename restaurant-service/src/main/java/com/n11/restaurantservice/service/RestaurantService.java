package com.n11.restaurantservice.service;

import com.n11.restaurantservice.dto.RestaurantResponse;
import com.n11.restaurantservice.dto.RestaurantSaveRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateAverageScoreRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateRequest;

import java.util.List;
/**
 * @author BeytullahBilek
 */
public interface RestaurantService {

    RestaurantResponse save(RestaurantSaveRequest restaurantSaveRequest);
    RestaurantResponse getById(String id);
    List<RestaurantResponse>getAll();
    RestaurantResponse update(String id , RestaurantUpdateRequest restaurantUpdateRequest);
    RestaurantResponse inactive(String id);
    void delete(String id);
    List<RestaurantResponse>getRecommendedRestaurantsByUserId(Long userId);
    RestaurantResponse updateRestaurantAverageScore(RestaurantUpdateAverageScoreRequest request);

}
