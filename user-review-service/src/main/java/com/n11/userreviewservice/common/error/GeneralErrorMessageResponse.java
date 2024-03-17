package com.n11.userreviewservice.common.error;

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
