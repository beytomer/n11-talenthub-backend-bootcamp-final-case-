package com.n11.userreviewservice.repository;

import com.n11.userreviewservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author BeytullahBilek
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review>findByRestaurantId(String restaurantId);

}
