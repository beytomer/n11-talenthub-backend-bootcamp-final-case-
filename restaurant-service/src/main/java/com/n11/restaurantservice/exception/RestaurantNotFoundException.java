package com.n11.restaurantservice.exception;

import com.n11.restaurantservice.common.error.GeneralErrorMessage;
/**
 * @author BeytullahBilek
 */
public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(GeneralErrorMessage message) {
        super(message.getMessage());
    }
}
