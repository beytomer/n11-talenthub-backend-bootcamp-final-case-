package com.n11.restaurantservice.common.error;

import java.time.LocalDateTime;
/**
 * @author BeytullahBilek
 */
public record GeneralErrorMessageResponse(
        LocalDateTime localDateTime,
        String message,
        String description

) {


}
