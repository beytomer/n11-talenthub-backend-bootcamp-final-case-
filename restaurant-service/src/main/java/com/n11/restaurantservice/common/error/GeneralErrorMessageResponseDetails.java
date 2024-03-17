package com.n11.restaurantservice.common.error;

import java.time.LocalDateTime;
import java.util.Map;
/**
 * @author BeytullahBilek
 */
public record GeneralErrorMessageResponseDetails(
        LocalDateTime date,
        Map<String,String> errorDetails,
        String message,
        String description
) {
}
