package com.n11.restaurantservice.mapper;

import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.dto.RestaurantResponse;
import com.n11.restaurantservice.dto.RestaurantSaveRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
/**
 * @author BeytullahBilek
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface RestaurantMapper {
    @Mapping(target = "isActive",constant = "true")
    @Mapping(target = "averageScore",constant = "0.0")
    Restaurant convertTorestaurant(RestaurantSaveRequest request);

    RestaurantResponse convertToRestaurantResponse(Restaurant restaurant);

    List<RestaurantResponse> convertToRestaurantResponseList(List<Restaurant>restaurantList);

    Restaurant convertUpdateToRestaurant(@MappingTarget Restaurant restaurant, RestaurantUpdateRequest request);


}
