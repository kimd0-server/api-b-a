package com.example.api.common.exception;

import com.example.api.common.base.dto.ErrorResponseDTO;
import com.example.api.common.base.vo.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("    URL   => " + request.getContextPath() + " | HttpMessageNotReadableException Error | NONE 처리 됐음");
        return handleExceptionInternal(ex, Code.INTERNAL_ERROR, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, Code.VALIDATION_ERROR, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        return handleExceptionInternal(e, e.getErrorCode(), request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        return handleExceptionInternal(e, Code.INTERNAL_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, Code.valueOf(status), headers, status, request);
    }


    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode,
                                                           WebRequest request) {
        return handleExceptionInternal(e, errorCode, HttpHeaders.EMPTY, errorCode.getHttpStatus(),
                request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode,
                                                           HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(
                e,
                ErrorResponseDTO.of(errorCode, errorCode.getMessage(e)),
                headers,
                status,
                request
        );
    }
}