package com.jumiapay.users.audit.domain.service;

import com.jumiapay.users.audit.domain.model.Audit;
import com.jumiapay.users.audit.domain.service.enums.DirectionEnum;

import java.util.List;

public interface AuditService {
    void create(Audit audit);
    void enqueue(Audit toAudit);
    List<Audit> getAuditsByUserId(String id, Integer page, Integer size, String[] sort, DirectionEnum direction);
    List<Audit> getAudits(Integer page, Integer size, String[] sort, DirectionEnum direction);
}
