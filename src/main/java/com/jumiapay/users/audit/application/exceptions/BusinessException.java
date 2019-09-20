package com.jumiapay.users.audit.application.exceptions;

import java.io.Serializable;

public class BusinessException extends RuntimeException implements Serializable {
    public BusinessException(String message) {
        super(message);
    }
}
