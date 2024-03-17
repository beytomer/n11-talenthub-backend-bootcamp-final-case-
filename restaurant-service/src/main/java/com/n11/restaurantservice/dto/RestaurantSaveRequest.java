package com.n11.restaurantservice.dto;

import javax.validation.constraints.NotBlank;
/**
 * @author BeytullahBilek
 */
public record RestaurantSaveRequest(
        @NotBlank(message = "Name cant be null or blank")
        String name,
        @NotBlank(message = "Location cant be null or blank")
        String location
) {
}
