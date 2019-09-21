package com.jumiapay.users.audit.domain.handler;

import com.jumiapay.users.audit.domain.model.Audit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PayloadHandler {

    public Audit maskSensitiveData(Audit audit) {

        Map<String, Object> payloadMap = replaceSentitiveValues(audit.getPayload());

        audit.setPayload(payloadMap);

        return audit;
    }

    private Map<String, Object> replaceSentitiveValues(Map<String, Object> payloadMap) {
        return payloadMap.entrySet()
                .stream()
                .peek(map -> {

                    Object value = map.getValue();

                    if (isSentitiveData(map.getKey().toLowerCase()))
                        map.setValue(String.valueOf(value).replaceAll(".", "*"));

                    if (value instanceof Map)
                        map.setValue(replaceSentitiveValues((Map<String, Object>) value));

                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean isSentitiveData(String key) {
        return key.contains("password") ||
                key.contains("card") ||
                key.contains("token");
    }

}
