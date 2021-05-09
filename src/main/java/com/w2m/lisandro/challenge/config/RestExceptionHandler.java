package com.w2m.lisandro.challenge.config;

import com.w2m.lisandro.challenge.handler.ApiError;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = { NoSuchElementException.class })
    public ResponseEntity<Object> handleNoSuchElement(HttpServletRequest request, NoSuchElementException ex ){

        return new ResponseEntity(ApiError.builder().status(HttpStatus.NOT_FOUND).message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(value = { EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(HttpServletRequest request, EmptyResultDataAccessException ex ){

        return new ResponseEntity(ApiError.builder().status(HttpStatus.NOT_FOUND).message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);
    }
}
