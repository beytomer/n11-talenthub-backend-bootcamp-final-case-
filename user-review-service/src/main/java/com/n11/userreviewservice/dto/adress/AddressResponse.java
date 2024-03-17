package com.n11.userreviewservice.dto.adress;
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
