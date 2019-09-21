package com.jumiapay.users.audit.domain.service.impl;

import com.jumiapay.users.audit.application.config.properties.messages.MessagesProperties;
import com.jumiapay.users.audit.application.exceptions.AuditNotFoundException;
import com.jumiapay.users.audit.domain.handler.PayloadHandler;
import com.jumiapay.users.audit.domain.model.Audit;
import com.jumiapay.users.audit.domain.repository.AuditRepository;
import com.jumiapay.users.audit.domain.service.AuditService;
import com.jumiapay.users.audit.domain.service.enums.DirectionEnum;
import com.jumiapay.users.audit.resource.queue.ProducerQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository repository;
    private final ProducerQueue producer;
    private final MessagesProperties messages;
    private final PayloadHandler handler;

    @Override
    public void create(Audit audit) {
        repository.create(audit.create());
    }

    @Override
    public void enqueue(Audit audit) {
        producer.send(handler.maskSensitiveData(audit));
    }

    @Override
    public List<Audit> getAuditsByUserId(String id, Integer page, Integer size, String[] sort, DirectionEnum direction) {
        return repository.getAuditsByUserId(id,page,size,sort,direction).orElseThrow(() -> new AuditNotFoundException(messages.getAuditNotFound()));
    }

    @Override
    public List<Audit> getAudits(Integer page, Integer size, String[] sort, DirectionEnum direction) {
        return repository.getAudits(page,size,sort,direction).orElseThrow(() -> new AuditNotFoundException(messages.getAuditNotFound()));
    }
}