package com.jumiapay.users.audit.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
public class Audit {
    private String id;
    private User user;
    private String action;
    @Setter
    private Map<String,Object> payload;
    private LocalDateTime createdDate;

    public Audit create(){
        this.id = UUID.randomUUID().toString();
        this.createdDate = LocalDateTime.now();
        return this;
    }
}
