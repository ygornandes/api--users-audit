package com.jumiapay.users.audit.resource.queue.activeMQ;

import com.jumiapay.users.audit.application.config.activeMq.message.AuditMessage;
import com.jumiapay.users.audit.domain.model.Audit;
import com.jumiapay.users.audit.resource.queue.ProducerQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActiveMQProducer implements ProducerQueue {

    private final JmsTemplate jms;

    @Override
    public void send(Audit audit) {
        jms.convertAndSend(AuditMessage.toMessage(audit));
    }
}
