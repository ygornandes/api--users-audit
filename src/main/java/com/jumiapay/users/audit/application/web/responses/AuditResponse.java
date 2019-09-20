package com.jumiapay.users.audit.application.web.responses;

import com.jumiapay.users.audit.domain.model.Audit;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class AuditResponse {
    private String id;
    private UserResponse user;
    private String action;
    private String payload;
    private String createdDate;

    public static List<AuditResponse> toListResponse(List<Audit> audits) {
        return audits.stream().map(AuditResponse::toResponse).collect(Collectors.toList());
    }

    private static AuditResponse toResponse(Audit audit) {
        DateTimeFormatter brazilianDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

        return AuditResponse
                .builder()
                .id(audit.getId())
                .user(UserResponse.toResponse(audit.getUser()))
                .action(audit.getAction())
                .payload(audit.getPayload())
                .createdDate(audit.getCreatedDate().format(brazilianDateTime))
                .build();
    }
}
