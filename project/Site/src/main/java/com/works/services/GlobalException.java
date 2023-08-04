package com.works.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
@Slf4j
public class GlobalException implements ErrorHandler {

    @Override
    @ExceptionHandler
    public void handleError(Throwable t) {
        System.err.println("System - handleError :" + t.getMessage());
        log.error("handleError",t.getMessage());
    }

}
