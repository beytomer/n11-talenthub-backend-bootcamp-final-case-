package com.n11.userreviewservice.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
