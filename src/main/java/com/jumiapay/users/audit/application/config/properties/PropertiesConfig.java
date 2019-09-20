package com.jumiapay.users.audit.application.config.properties;

import com.jumiapay.users.audit.application.config.properties.activeMq.ActiveMqProperties;
import com.jumiapay.users.audit.application.config.properties.messages.MessagesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ActiveMqProperties.class, MessagesProperties.class})
public class PropertiesConfig {
}
