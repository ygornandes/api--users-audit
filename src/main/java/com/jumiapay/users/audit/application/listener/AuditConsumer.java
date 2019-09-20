package com.jumiapay.users.audit.application.listener;

import com.jumiapay.users.audit.application.config.activeMq.message.AuditMessage;
import com.jumiapay.users.audit.domain.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Log
@RequiredArgsConstructor
@Component
public class AuditConsumer {

    private final AuditService service;

    @JmsListener(destination = "${activemq.queue}")
    public void receive(AuditMessage message){
        log.info("received message= "+message.toString());
        service.create(message.toAudit());
    }
}
