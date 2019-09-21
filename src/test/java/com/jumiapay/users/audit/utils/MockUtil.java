package com.jumiapay.users.audit.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumiapay.users.audit.domain.model.Audit;
import com.jumiapay.users.audit.domain.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MockUtil {
    public static Audit getAudit(String userId, String type) throws IOException {
        return Audit.builder()
                .user(getUser(userId, type))
                .action("TESTE")
                .payload(deserialize(getRequestBody("json/auditJsonSuccess.json")))
                .build();
    }

    public static User getUser(String userId, String type) {
        return User.builder()
                .id(userId)
                .type(type)
                .build();
    }

    public static String getRequestBody(String path) throws IOException {
        InputStream inputStream = MockUtil.class.getClassLoader().getResourceAsStream(path);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    public static Audit getAuditValidPayload(String userId, String type) throws IOException {
        return Audit.builder()
                .user(getUser(userId, type))
                .action("TESTE")
                .payload(deserialize(getRequestBody("json/payloadWithSuccess.json")))
                .build();
    }

    public static Audit getAuditValidPayloadWithSentitiveValues(String userId, String type) throws IOException {
        return Audit.builder()
                .user(getUser(userId, type))
                .action("TESTE")
                .payload( deserialize(getRequestBody("json/payloadWithSensitiveValues.json")))
                .build();
    }

    public static Optional<List<Audit>> getOptionalAudits() {
        return Optional.of(IntStream.range(0, 3).mapToObj(i -> getAuditWithId("George", "CUSTOMER")).collect(Collectors.toList()));
    }

    private static Audit getAuditWithId (String userId, String type){
        return Audit.builder()
                .id("fdkfodfkodkfodsk")
                .user(getUser(userId, type))
                .action("TESTE")
                .payload(Collections.emptyMap())
                .createdDate(LocalDateTime.now())
                .build();
    }

    private static Map<String, Object> deserialize(String payload) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(payload, new TypeReference<LinkedHashMap<String, Object>>(){} );
    }
}