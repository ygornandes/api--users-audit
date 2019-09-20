package com.jumiapay.users.audit.application.exceptions;

public class PayloadInvalidException extends RuntimeException {
    public PayloadInvalidException(String message) {
        super(message);
    }
}
