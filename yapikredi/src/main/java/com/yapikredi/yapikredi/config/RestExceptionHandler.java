package com.yapikredi.yapikredi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

@ControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity onValidationError(Exception ex ,Locale locale) {
        ex.printStackTrace();

        String error = messageSource.getMessage("ConstraintViolationException", null, locale);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValid(Exception ex, Locale locale) {
        ex.printStackTrace();

        String error = messageSource.getMessage("MethodArgumentNotValidException", null, locale);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);

    }


    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity internalError(Exception ex, Locale locale) {
        ex.printStackTrace();

        String error = messageSource.getMessage("HttpServerErrorException", null, locale);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);

    }





}