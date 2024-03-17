package com.n11.userreviewservice.exception;

import com.n11.userreviewservice.common.error.GeneralErrorMessage;
/**
 * @author BeytullahBilek
 */
public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(GeneralErrorMessage message) {
        super(message.getMessage());
    }
}
