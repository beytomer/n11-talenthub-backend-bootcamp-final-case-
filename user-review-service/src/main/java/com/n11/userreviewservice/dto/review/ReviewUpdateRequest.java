package com.n11.userreviewservice.dto.review;

import jakarta.validation.constraints.*;
/**
 * @author BeytullahBilek
 */
public record ReviewUpdateRequest(
        @NotNull
        @Min(value = 1, message = "Rate must be between 1 and 5")
        @Max(value = 5, message = "Rate must be between 1 and 5")
        byte rate,
        @Size(max = 150)
        @NotBlank(message = "Comment cant be null or blank")
        String comment


) {
}
