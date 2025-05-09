package io.ssafy.p.j11a307.announcement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(final BusinessException e) {
        return new ResponseEntity<>(e.getMessage(), e.getErrorCode().getStatus());
    }
}
