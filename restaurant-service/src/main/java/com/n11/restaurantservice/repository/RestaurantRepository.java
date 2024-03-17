package com.n11.restaurantservice.repository;

import com.n11.restaurantservice.entity.Restaurant;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;
/**
 * @author BeytullahBilek
 */
public interface RestaurantRepository extends SolrCrudRepository<Restaurant, String> {

    @Query(requestHandler = "fq={!geofilt pt=?0 sfield=location d=10}&sort={sum(mul(averageScore,14),mul(sub(10,geodist(?0,location)),3)) desc", value = "*:*")
    List<Restaurant> getRecommendedRestaurants(String location);

}
