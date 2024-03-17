package com.n11.userreviewservice.client;

import com.n11.userreviewservice.dto.restaurant.RestaurantUpdateAverageScoreRequest;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * @author BeytullahBilek
 */
@FeignClient(name = "restaurant-service",url = "http://localhost:8080/api/v1/restaurants")
public interface RestaurantClient {

    @PutMapping("/averageScore/{restaurantId}")
    Response updateRestaurantAverageScore(@PathVariable String restaurantId, @RequestBody RestaurantUpdateAverageScoreRequest request);

}




