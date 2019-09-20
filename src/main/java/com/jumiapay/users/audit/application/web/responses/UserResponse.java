package com.jumiapay.users.audit.application.web.responses;

import com.jumiapay.users.audit.domain.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String type;

    public static UserResponse toResponse(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .type(user.getType())
                .build();
    }
}
