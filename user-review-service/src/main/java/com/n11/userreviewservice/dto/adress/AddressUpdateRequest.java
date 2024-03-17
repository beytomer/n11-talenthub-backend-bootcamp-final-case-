package com.n11.userreviewservice.dto.adress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/**
 * @author BeytullahBilek
 */
public record AddressUpdateRequest(

        @NotBlank(message = "City cant be null or blank")
        @Size(max=60)
        String city,
        @NotBlank(message = "County cant be null or blank")
        @Size(max = 60)
        String county,
        @NotBlank(message = "location cant be null or blank")
        String location
) {
}
