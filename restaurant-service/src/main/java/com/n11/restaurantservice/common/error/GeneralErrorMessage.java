package com.n11.restaurantservice.common.error;

/**
 * @author BeytullahBilek
 */
public enum GeneralErrorMessage implements BaseErrorMessage{

    RESTAURANT_NOT_FOUND("Restaurant Not Found");
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
