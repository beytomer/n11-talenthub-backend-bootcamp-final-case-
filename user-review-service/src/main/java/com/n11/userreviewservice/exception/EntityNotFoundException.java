package com.n11.userreviewservice.exception;

import com.n11.userreviewservice.common.error.GeneralErrorMessage;
/**
 * @author BeytullahBilek
 */
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(GeneralErrorMessage message) {
        super(message.getMessage());
    }
}
