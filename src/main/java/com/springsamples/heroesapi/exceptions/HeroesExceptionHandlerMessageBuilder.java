package com.springsamples.heroesapi.exceptions;

import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

public class HeroesExceptionHandlerMessageBuilder {
    protected static String buildConstraintViolationMessage(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(". "));
    }

    protected static String buildTypeMismatchMessage(TypeMismatchException ex) {
        var invalidValue = ex.getValue();
        var field = ((MethodArgumentTypeMismatchException) ex).getName();
        return String.format("Invalid value %s for field %s", invalidValue, field);
    }

    protected static String buildMethodArgumentNotValidMessage(MethodArgumentNotValidException exception) {
        StringBuilder result = new StringBuilder();
        for (var fieldError: exception.getBindingResult().getFieldErrors()
        ) {
            result.append(fieldError.getDefaultMessage());
        }
        return result.toString();
    }
}
