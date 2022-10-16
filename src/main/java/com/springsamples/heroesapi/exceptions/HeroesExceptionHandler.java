package com.springsamples.heroesapi.exceptions;

import com.springsamples.heroesapi.exceptions.model.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class HeroesExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ HeroNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDto handleHeroNotFound(HeroNotFoundException ex) {
        return ErrorDto.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .reason(ex.getMessage())
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var invalidValue = ex.getValue();
        var field = ((MethodArgumentTypeMismatchException) ex).getName();
        var output = String.format("Invalid value %s for field %s", invalidValue, field);
        log.info(output);
        return ResponseEntity.badRequest()
                .body(ErrorDto.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .reason(output)
                        .build());
    }
}
