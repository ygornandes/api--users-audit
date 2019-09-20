package com.jumiapay.users.audit.application.web.requests;

import com.jumiapay.users.audit.application.web.utils.Constants;
import com.jumiapay.users.audit.domain.model.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserRequest {

    @NotBlank(message = Constants.USER_ID_NOT_BLANCK)
    private String id;
    @NotBlank(message = Constants.USER_TYPE_NOT_BLANCK)
    private String type;

    public User toUser() {
        return User.builder()
                .id(this.getId())
                .type(this.getType())
                .build();
    }
}
