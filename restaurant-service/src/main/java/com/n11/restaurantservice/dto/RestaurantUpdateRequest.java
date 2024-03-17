package com.n11.restaurantservice.dto;

import javax.validation.constraints.NotBlank;
/**
 * @author BeytullahBilek
 */
public record RestaurantUpdateRequest(
        @NotBlank(message = "Name cant be null or blank!")
        String name,
        @NotBlank(message = "Name cant be null or blank!")
        String location
) {
}
