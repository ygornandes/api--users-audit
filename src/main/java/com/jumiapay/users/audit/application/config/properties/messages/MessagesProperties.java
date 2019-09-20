package com.jumiapay.users.audit.application.config.properties.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("messages")
public class MessagesProperties {
    private String requestBodyRequired;
    private String auditNotFound;
    private String pathParamRequired;
    private String queryParamRequired;
    private String invalidPayload;
    private String serializeError;
}
