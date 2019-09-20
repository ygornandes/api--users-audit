package com.jumiapay.users.audit.infrastructure.mongodb.repository.impl;

import com.jumiapay.users.audit.domain.model.Audit;
import com.jumiapay.users.audit.domain.repository.AuditRepository;
import com.jumiapay.users.audit.domain.service.enums.DirectionEnum;
import com.jumiapay.users.audit.infrastructure.mongodb.document.AuditDocument;
import com.jumiapay.users.audit.infrastructure.mongodb.repository.AuditMongoRepository;
import com.jumiapay.users.audit.infrastructure.mongodb.repository.factory.SortFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {

    private final AuditMongoRepository repository;
    private final SortFactory sortFactory;

    @Override
    public void create(Audit audit) {
        repository.save(AuditDocument.toDocument(audit));
    }

    @Override
    public Optional<List<Audit>> getAuditsByUserId(String id, Integer page, Integer size, String[] sort, DirectionEnum direction) {
        Page<AuditDocument> pageAudits = repository.findAllByUserId(id, PageRequest.of(page, size,sortFactory.getSort(sort,direction)));

        return Optional.ofNullable(pageAudits.isEmpty() ? null : AuditDocument.toListAudit(pageAudits));

    }

    @Override
    public Optional<List<Audit>> getAudits(Integer page, Integer size, String[] sort, DirectionEnum direction) {
        Page<AuditDocument> pageAudits = repository.findAll(PageRequest.of(page, size,sortFactory.getSort(sort,direction)));

        return Optional.ofNullable(pageAudits.isEmpty() ? null : AuditDocument.toListAudit(pageAudits));
    }
}
