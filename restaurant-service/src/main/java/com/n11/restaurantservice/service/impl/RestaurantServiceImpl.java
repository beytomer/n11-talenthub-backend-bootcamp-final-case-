package com.n11.restaurantservice.service.impl;

import com.n11.restaurantservice.client.AddressFeignClient;
import com.n11.restaurantservice.client.SolrClientService;
import com.n11.restaurantservice.common.error.BaseRestResponse;
import com.n11.restaurantservice.common.error.GeneralErrorMessage;
import com.n11.restaurantservice.dto.*;
import com.n11.restaurantservice.entity.Restaurant;
import com.n11.restaurantservice.exception.RestaurantNotFoundException;
import com.n11.restaurantservice.mapper.RestaurantMapper;
import com.n11.restaurantservice.repository.RestaurantRepository;
import com.n11.restaurantservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;
/**
 * @author BeytullahBilek
 */
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final AddressFeignClient addressFeignClient;
    private final SolrClientService solrClientService;

    @Override
    public RestaurantResponse save(RestaurantSaveRequest restaurantSaveRequest) {

        Restaurant restaurant =restaurantMapper.convertTorestaurant(restaurantSaveRequest);
         restaurant= restaurantRepository.save(restaurant);

        return restaurantMapper.convertToRestaurantResponse(restaurant);

    }

    @Override
    public RestaurantResponse getById(String id) {
        Restaurant restaurant=getRestaurantById(id);
        return restaurantMapper.convertToRestaurantResponse(restaurant);
    }

    @Override
    public List<RestaurantResponse> getAll() {

        List<Restaurant>restaurantList= StreamSupport
                .stream(restaurantRepository.findAll().spliterator(),false)
                .toList();

        return restaurantMapper.convertToRestaurantResponseList(restaurantList);

    }

    @Override
    public RestaurantResponse update(String id, RestaurantUpdateRequest restaurantUpdateRequest) {
        Restaurant restaurant = getRestaurantById(id);
        Restaurant updatedRestaurant = restaurantMapper.convertUpdateToRestaurant(restaurant, restaurantUpdateRequest);
        restaurantRepository.save(updatedRestaurant);
        return restaurantMapper.convertToRestaurantResponse(updatedRestaurant);
    }

    @Override
    public RestaurantResponse inactive(String id) {
       Restaurant restaurant =getRestaurantById(id);
       restaurant.setIsActive(false);
       restaurantRepository.save(restaurant);
       return restaurantMapper.convertToRestaurantResponse(restaurant);
    }

    @Override
    public void delete(String id) {
        Restaurant restaurant = getRestaurantById(id);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<RestaurantResponse> getRecommendedRestaurantsByUserId(Long userId) {
        addressFeignClient.getAddressesByUserId(userId);
        ResponseEntity<BaseRestResponse<List<AddressResponse>>>addressDTOResponse= addressFeignClient.getAddressesByUserId(userId);
        List<AddressResponse> addressResponsesList = Objects.requireNonNull(addressDTOResponse.getBody().getData());
        AddressResponse firstAddress= addressResponsesList.get(0);
        String userLocation = firstAddress.location().replaceAll(",\\s+",",");
        List<RestaurantResponse> restaurantList = solrClientService.solrQuery(userLocation);
        return restaurantList;
    }

    @Override
    public RestaurantResponse updateRestaurantAverageScore(RestaurantUpdateAverageScoreRequest request) {
        Restaurant restaurant=restaurantRepository.findById(request.restaurantId()).orElseThrow(() ->new RestaurantNotFoundException(GeneralErrorMessage.RESTAURANT_NOT_FOUND) );
        restaurant.setAverageScore(request.averageScore());
        restaurantRepository.save(restaurant);
        return restaurantMapper.convertToRestaurantResponse(restaurant);
    }

    private Restaurant getRestaurantById(String id){
        return restaurantRepository.findById(id).orElseThrow(() ->new RestaurantNotFoundException(GeneralErrorMessage.RESTAURANT_NOT_FOUND));
    }


}
