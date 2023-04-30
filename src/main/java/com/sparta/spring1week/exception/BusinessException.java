package com.sparta.spring1week.exception;

import lombok.Builder;
import lombok.Getter;

/**
 * 에러를 사용하기 위한 구현체
 */
public class BusinessException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    @Builder
    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}