package com.jvo.store.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        return super.handleBindException(ex, headers, status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleValidationException(ConstraintViolationException exception) {
        return ExceptionDto.builder()
                .errorCode(HttpStatus.BAD_REQUEST.toString())
                .messages(getValidationErrors(exception))
                .build();
    }

    private List<String> getValidationErrors(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream()
                .map(this::getErrorMessage)
                .collect(toList());
    }

    private String getErrorMessage(ConstraintViolation<?> violation) {
        return "Error occurred: "
                + violation.getPropertyPath() + ": "
                + violation.getInvalidValue() + " "
                + violation.getMessage();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleValidationException(ProductNotFoundException exception) {
        return ExceptionDto.builder()
                .errorCode(HttpStatus.NOT_FOUND.toString())
                .messages(newArrayList(exception.getMessage()))
                .build();
    }
}