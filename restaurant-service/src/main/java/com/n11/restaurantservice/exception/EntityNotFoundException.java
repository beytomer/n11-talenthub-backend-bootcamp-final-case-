package com.n11.restaurantservice.exception;

import com.n11.restaurantservice.common.error.GeneralErrorMessage;
/**
 * @author BeytullahBilek
 */
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(GeneralErrorMessage message) {
        super(message.getMessage());
    }
}
