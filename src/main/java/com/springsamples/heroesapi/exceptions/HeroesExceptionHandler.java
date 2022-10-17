package com.springsamples.heroesapi.exceptions;

import com.springsamples.heroesapi.exceptions.model.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static com.springsamples.heroesapi.exceptions.HeroesExceptionHandlerMessageBuilder.*;

@RestControllerAdvice
@Slf4j
public class HeroesExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({HeroNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleHeroNotFound(HeroNotFoundException ex) {
        var message = ex.getMessage();
        log.info(message);
        return ErrorDto.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .reason(message)
                .build();
    }

    @ExceptionHandler({ DataAccessException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto handleDataAccessException(DataAccessException ex) {
        var message = ex.getMessage();
        log.info(message);
        return ErrorDto.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .reason(message)
                .build();
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleConstraintViolation(ConstraintViolationException ex) {
        var message = buildConstraintViolationMessage(ex);
        log.info(message);
        return ErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .reason(message)
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var message = buildTypeMismatchMessage(ex);
        log.info(message);
        return ResponseEntity.badRequest()
                .body(ErrorDto.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .reason(message)
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var message = buildMethodArgumentNotValidMessage(ex);
        log.info(message);
        return ResponseEntity.badRequest()
                .body(ErrorDto.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .reason(message)
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var message = "Invalid UUID";
        log.info(message);
        return ResponseEntity.badRequest()
                .body(ErrorDto.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .reason(message)
                        .build());
    }
}
