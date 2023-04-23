package com.sparta.spring1week.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.io.IOException;


/**
 * Controller 내에서 발생하는 Exception 대해서 Catch 하여 응답값(Response)을 보내주는 기능을 수행함.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * BusinessException에서 발생한 에러
     *
     * @param ex BusinessException
     * @return ResponseEntity
     */
    @ExceptionHandler(BusinessExceptionHandler.class)
    public ResponseEntity<ErrorResponse> handleCustomException(BusinessExceptionHandler ex) {
        log.debug("===========================================================");
        log.debug("여기로 오는가?!");
        log.debug("===========================================================");

        final ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}