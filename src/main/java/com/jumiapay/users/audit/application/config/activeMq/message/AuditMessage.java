package com.jumiapay.users.audit.application.config.activeMq.message;

import com.jumiapay.users.audit.domain.model.Audit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditMessage implements Serializable {
    private String id;
    private UserMessage user;
    private String action;
    private String payload;
    private LocalDateTime createdDate;

    public static AuditMessage toMessage(Audit audit){
        return AuditMessage
                .builder()
                .user(UserMessage.toMessage(audit.getUser()))
                .action(audit.getAction())
                .payload(audit.getPayload())
                .build();
    }

    public Audit toAudit(){
        return Audit.builder()
                .user(this.getUser().toUser())
                .action(this.getAction())
                .payload(this.getPayload())
                .build();

    }


}
