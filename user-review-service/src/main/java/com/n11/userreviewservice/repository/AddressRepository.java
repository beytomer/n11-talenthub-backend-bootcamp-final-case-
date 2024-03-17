package com.n11.userreviewservice.repository;

import com.n11.userreviewservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author BeytullahBilek
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "SELECT * FROM addresses WHERE user_id = :userId", nativeQuery = true)
    List<Address>findByUserId(@Param("userId") Long userId);
}
