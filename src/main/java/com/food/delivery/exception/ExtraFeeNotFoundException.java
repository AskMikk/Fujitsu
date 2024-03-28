package com.food.delivery.exception;

public class ExtraFeeNotFoundException extends RuntimeException {
    public ExtraFeeNotFoundException(String message) {
        super(message);
    }
}
