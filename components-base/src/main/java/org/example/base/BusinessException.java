package org.example.base;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final int code;
    private final String message;

    public BusinessException(String message) {
        this.code = 500;
        this.message = message;
    }

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}