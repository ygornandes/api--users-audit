package com.jumiapay.users.audit.application.web.requests;

import com.jumiapay.users.audit.application.web.utils.Constants;
import com.jumiapay.users.audit.domain.model.Audit;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class AuditRequest {

    @NotNull(message = Constants.USER_NOT_NULL)
    private UserRequest user;
    @NotBlank(message = Constants.ACTION_NOT_BLANCK)
    private String action;
    @NotBlank(message = Constants.PAYLOAD_NOT_BLANCK)
    private String payload;

    public Audit toAudit(){
        return Audit.builder()
                .user(this.getUser().toUser())
                .action(this.getAction())
                .payload(this.getPayload())
                .build();

    }
}
