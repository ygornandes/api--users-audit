package com.jumiapay.users.audit.domain.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumiapay.users.audit.application.config.properties.messages.MessagesProperties;
import com.jumiapay.users.audit.application.exceptions.PayloadInvalidException;
import com.jumiapay.users.audit.domain.model.Audit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PayloadHandler {

    private ObjectMapper mapper = new ObjectMapper();

    private final MessagesProperties messages;

    public Audit maskSensitiveData(Audit audit) {

        Map<String, Object> payloadMap = replaceSentitiveValues(deserialize(audit.getPayload()));

        audit.setPayload(serialize(payloadMap));

        return audit;
    }

    private Map<String, Object> replaceSentitiveValues(Map<String, Object> payloadMap) {
       return payloadMap.entrySet()
                .stream()
                .peek(map -> {
                    if (map.getKey().toLowerCase().contains("password") ||
                            map.getKey().toLowerCase().contains("card") ||
                            map.getKey().toLowerCase().contains("token"))
                        map.setValue(String.valueOf(map.getValue()).replaceAll(".", "*"));

                    if (map.getValue() instanceof Map)
                        map.setValue(replaceSentitiveValues((Map<String, Object>) map.getValue()));

                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String serialize(Map<String, Object> payloadMap) {
        try {
            return mapper.writeValueAsString(payloadMap);
        } catch (JsonProcessingException e) {
            throw new PayloadInvalidException(messages.getSerializeError());
        }
    }

    private Map<String, Object> deserialize(String payload) {
      try {
            return mapper.readValue(payload, new TypeReference<LinkedHashMap<String, Object>>(){} );
        } catch (IOException e) {
            throw new PayloadInvalidException(messages.getInvalidPayload());
        }
    }
}
