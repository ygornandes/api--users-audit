package com.jumiapay.users.audit.application.config.properties.activeMq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("activemq")
public class ActiveMqProperties {
    private String broker;
    private String username;
    private String password;
    private String queue;
    private Integer prefetchLimit;
    private Integer maxRetries;
    private Integer redeliveryDelay;
}
