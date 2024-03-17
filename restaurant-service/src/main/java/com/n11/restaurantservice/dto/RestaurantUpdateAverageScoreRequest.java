package com.n11.restaurantservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * @author BeytullahBilek
 */
public record RestaurantUpdateAverageScoreRequest(
        @NotBlank(message = "restaurantId cant be null or blank")
        String restaurantId,
        @NotNull
        Double averageScore
) {
}
