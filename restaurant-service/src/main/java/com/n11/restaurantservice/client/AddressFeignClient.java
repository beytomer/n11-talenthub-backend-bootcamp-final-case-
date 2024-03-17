package com.n11.restaurantservice.client;

import com.n11.restaurantservice.common.error.BaseRestResponse;
import com.n11.restaurantservice.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
/**
 * @author BeytullahBilek
 */
@FeignClient(name = "user-review-service",url="http://localhost:8081/api/v1/addresses")
public interface AddressFeignClient {

    @GetMapping("/with-userId/{userId}")
    ResponseEntity<BaseRestResponse<List<AddressResponse>>> getAddressesByUserId(@PathVariable Long userId);
}
