package com.jumiapay.users.audit.infrastructure.mongodb.repository;

import com.jumiapay.users.audit.infrastructure.mongodb.document.AuditDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditMongoRepository extends MongoRepository<AuditDocument,String> {
    Page<AuditDocument> findAllByUserId(String id, Pageable pageable);
}
