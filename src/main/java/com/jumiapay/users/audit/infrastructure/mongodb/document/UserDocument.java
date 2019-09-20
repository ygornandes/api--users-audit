package com.jumiapay.users.audit.infrastructure.mongodb.document;

import com.jumiapay.users.audit.domain.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDocument {
    private String id;
    private String type;

    public static UserDocument toDocument(User user) {
        return UserDocument
                .builder()
                .id(user.getId())
                .type(user.getType())
                .build();
    }

    public User toUser() {
        return User.builder()
                .id(this.getId())
                .type(this.getType())
                .build();
    }
}
