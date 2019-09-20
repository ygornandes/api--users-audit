package com.jumiapay.users.audit.application.config.activeMq.message;

import com.jumiapay.users.audit.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMessage implements Serializable {
    private String id;
    private String type;

    public static UserMessage toMessage(User user) {
        return UserMessage.builder()
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
