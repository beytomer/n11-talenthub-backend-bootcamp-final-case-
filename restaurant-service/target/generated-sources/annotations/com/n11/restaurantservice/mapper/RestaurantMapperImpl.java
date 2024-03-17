package com.n11.restaurantservice.mapper;

import com.n11.restaurantservice.dto.RestaurantResponse;
import com.n11.restaurantservice.dto.RestaurantSaveRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateRequest;
import com.n11.restaurantservice.entity.Restaurant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-17T03:03:38+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class RestaurantMapperImpl implements RestaurantMapper {

    @Override
    public Restaurant convertTorestaurant(RestaurantSaveRequest request) {
        if ( request == null ) {
            return null;
        }

        Restaurant restaurant = new Restaurant();

        restaurant.setName( request.name() );
        restaurant.setLocation( request.location() );

        restaurant.setIsActive( true );
        restaurant.setAverageScore( (double) 0.0 );

        return restaurant;
    }

    @Override
    public RestaurantResponse convertToRestaurantResponse(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        String id = null;
        String name = null;
        String location = null;
        Double averageScore = null;

        id = restaurant.getId();
        name = restaurant.getName();
        location = restaurant.getLocation();
        averageScore = restaurant.getAverageScore();

        RestaurantResponse restaurantResponse = new RestaurantResponse( id, name, location, averageScore );

        return restaurantResponse;
    }

    @Override
    public List<RestaurantResponse> convertToRestaurantResponseList(List<Restaurant> restaurantList) {
        if ( restaurantList == null ) {
            return null;
        }

        List<RestaurantResponse> list = new ArrayList<RestaurantResponse>( restaurantList.size() );
        for ( Restaurant restaurant : restaurantList ) {
            list.add( convertToRestaurantResponse( restaurant ) );
        }

        return list;
    }

    @Override
    public Restaurant convertUpdateToRestaurant(Restaurant restaurant, RestaurantUpdateRequest request) {
        if ( request == null ) {
            return restaurant;
        }

        restaurant.setName( request.name() );
        restaurant.setLocation( request.location() );

        return restaurant;
    }
}
