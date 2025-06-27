package com.compassuol.smart_parking_api.exceptions;

public class UsernameUniqueViolationException extends RuntimeException {
    public UsernameUniqueViolationException(String msg) {
        super(msg);
    }
}
