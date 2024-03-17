package com.n11.restaurantservice.dto;
/**
 * @author BeytullahBilek
 */
public record AddressResponse(
        Long id,
        String city,
        String county,
        String location

) {
}
