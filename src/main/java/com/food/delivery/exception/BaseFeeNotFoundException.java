package com.food.delivery.exception;

public class BaseFeeNotFoundException extends RuntimeException {
    public BaseFeeNotFoundException(String message) {
        super(message);
    }
}
