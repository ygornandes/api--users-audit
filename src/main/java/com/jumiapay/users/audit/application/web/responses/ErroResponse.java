package com.jumiapay.users.audit.application.web.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
public class ErroResponse {
    private Date timestamp;
    private Integer status;
    private String message;
    private List<String> errors;
}
