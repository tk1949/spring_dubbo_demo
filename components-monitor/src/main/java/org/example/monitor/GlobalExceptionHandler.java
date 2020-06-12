package org.example.monitor;

import org.example.base.BusinessException;
import org.example.base.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler  {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResultMessage<String> resolveBusinessException(Exception ex) {
        log.error("GlobalExceptionHandler > {}", ex.getMessage());
        return ResultMessage.buildErrorMessage(500, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultMessage<String> resolveException(Exception ex) {
        log.error("GlobalExceptionHandler > ", ex);
        return ResultMessage.message_ERROR;
    }
}