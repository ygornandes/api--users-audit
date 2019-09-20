package com.jumiapay.users.audit.infrastructure.mongodb.document;

import com.jumiapay.users.audit.domain.model.Audit;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@Document("audit")
public class AuditDocument {

    @Id
    private String id;
    private UserDocument user;
    private String action;
    private String payload;
    private LocalDateTime createdDate;

    public static AuditDocument toDocument(Audit audit) {
        return AuditDocument.builder()
                .id(audit.getId())
                .user(UserDocument.toDocument(audit.getUser()))
                .action(audit.getAction())
                .payload(audit.getPayload())
                .createdDate(audit.getCreatedDate())
                .build();
    }

    public static List<Audit> toListAudit(Page<AuditDocument> pageAudits) {
        return pageAudits.get()
                .map(AuditDocument::toAudit)
                .collect(Collectors.toList());
    }

    private static Audit toAudit(AuditDocument auditDocument) {
       return Audit.builder()
               .id(auditDocument.getId())
               .user(auditDocument.getUser().toUser())
               .action(auditDocument.getAction())
               .payload(auditDocument.getPayload())
               .createdDate(auditDocument.getCreatedDate())
               .build();
    }
}
