package com.jumiapay.users.audit.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private String id;
    private String type;
}
