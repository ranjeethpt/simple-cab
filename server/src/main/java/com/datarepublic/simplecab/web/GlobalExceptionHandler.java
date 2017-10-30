package com.datarepublic.simplecab.web;

import com.datarepublic.simplecab.web.controller.InvalidDateFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ranjeethpt on 29/10/17.
 *
 * @author ranjeethpt
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<String> invalidDate(HttpServletRequest req, Exception exception) {
        log.error("Exception for request {}, exception is {}", req, exception);
        return new ResponseEntity<>("Invalid date format. Expected yyyy.mm-dd. ", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class,
            HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<String> badRequest(HttpServletRequest req, Exception exception) {
        log.warn("Exception for request {}, exception is {}", req, exception);
        return new ResponseEntity<>("Bad request. Supported API call is POST Content type 'application/json' /services/retrieve-count/{yyyy-mm-dd}/ignoreCacheFlag with body containing Set of Strings.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> serverErrorRequest(HttpServletRequest req, Exception exception) {
        log.error("Exception for request {}, exception is {}", req, exception);
        return new ResponseEntity<>("Unknown error has occurred. Please contact support. ", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}