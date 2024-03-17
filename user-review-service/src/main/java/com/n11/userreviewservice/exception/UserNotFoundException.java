package com.n11.userreviewservice.exception;

import com.n11.userreviewservice.common.error.GeneralErrorMessage;
/**
 * @author BeytullahBilek
 */
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(GeneralErrorMessage message) {
        super(message.getMessage());
    }
}
