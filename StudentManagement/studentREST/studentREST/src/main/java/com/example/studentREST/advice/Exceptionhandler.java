package com.example.studentREST.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class Exceptionhandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> constraintvalidator(ConstraintViolationException ex){
        Map<String, String> map = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldname = violation.getPropertyPath().toString();
            String errormessage = violation.getMessage();
            map.put(fieldname, errormessage);
        });
        return map;
    }
}
