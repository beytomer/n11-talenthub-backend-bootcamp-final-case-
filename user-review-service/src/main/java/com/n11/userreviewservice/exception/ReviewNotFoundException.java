package com.n11.userreviewservice.exception;

import com.n11.userreviewservice.common.error.GeneralErrorMessage;
/**
 * @author BeytullahBilek
 */
public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(GeneralErrorMessage message) {
        super(message.getMessage());
    }
}
