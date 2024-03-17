package com.n11.userreviewservice.common.error;
/**
 * @author BeytullahBilek
 */
public enum GeneralErrorMessage implements BaseErrorMessage{

    USER_NOT_FOUND("user.not.found"),
    ADDRESS_NOT_FOUND("address.not.found"),
    REVIEW_NOT_FOUND("review.not.found");

    private final String message;

    GeneralErrorMessage(String message) {
        this.message = message;
    }
    @Override
    public String getMessage(){
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
