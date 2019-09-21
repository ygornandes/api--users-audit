package com.jumiapay.users.audit.application.exceptions;

import java.io.Serializable;

public class AuditNotFoundException extends RuntimeException implements Serializable {
    public AuditNotFoundException(String message) {
        super(message);
    }
}
