package com.sparta.crudbasedjwt.Exception;

public class ExampleException extends RuntimeException {
    public ExampleException(Integer errorCode, String message) {
        super(String.format("Error %d: %s", errorCode, message));
    }
}
