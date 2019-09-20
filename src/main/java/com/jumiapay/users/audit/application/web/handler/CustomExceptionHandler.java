package com.jumiapay.users.audit.application.web.handler;

import com.jumiapay.users.audit.application.config.properties.messages.MessagesProperties;
import com.jumiapay.users.audit.application.exceptions.BusinessException;
import com.jumiapay.users.audit.application.web.responses.ErroResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessagesProperties messages;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(createErrorResponse(status,errors,null), headers, status);
    }

    private ErroResponse createErrorResponse(HttpStatus status, List<String> errors, String message) {
        return ErroResponse.builder()
                .message(message)
                .status(status.value())
                .timestamp( new Date())
                .errors(errors)
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return  new ResponseEntity<>(createErrorResponse(status,null, messages.getRequestBodyRequired()), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(createErrorResponse(status,null, messages.getQueryParamRequired()), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(createErrorResponse(status,null, messages.getPathParamRequired()), headers, status);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleComplaintNotFoundException(BusinessException be, WebRequest request){
        return new ResponseEntity<>(createErrorResponse(HttpStatus.NOT_FOUND,null,be.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {

        List<String> errors = ex.getConstraintViolations().stream().map(cv -> cv.getMessage()).collect(Collectors.toList());

        return new ResponseEntity<>(createErrorResponse(HttpStatus.BAD_REQUEST,errors,null), HttpStatus.BAD_REQUEST);
    }
}

