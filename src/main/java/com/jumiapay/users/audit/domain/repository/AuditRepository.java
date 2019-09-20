package com.jumiapay.users.audit.domain.repository;

import com.jumiapay.users.audit.domain.model.Audit;
import com.jumiapay.users.audit.domain.service.enums.DirectionEnum;

import java.util.List;
import java.util.Optional;

public interface AuditRepository {
    void create(Audit audit);
    Optional<List<Audit>> getAuditsByUserId(String id, Integer page, Integer size, String[] sort, DirectionEnum direction);
    Optional<List<Audit>> getAudits(Integer page, Integer size, String[] sort, DirectionEnum direction);
}
