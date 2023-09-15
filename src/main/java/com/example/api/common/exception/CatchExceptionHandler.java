package com.example.api.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice("com.example.api")
public class CatchExceptionHandler {
    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public void exceptionHandle(HttpServletRequest req) {
        log.error("    URL   => " + req.getRequestURL() + " | HttpMessageNotReadableException Error | NONE 처리 됐음");
    }
}
