package com.renchiiks.spring6restmvcmaven.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler
    ResponseEntity handleJPAValidationException(TransactionSystemException exception){
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();
        if (exception.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException =
                    (ConstraintViolationException) exception.getCause().getCause();
            List<Map<String,String>>errors = constraintViolationException
                    .getConstraintViolations().stream()
                    .map(constraintViolation ->{
                        Map<String,String>  errMap = new HashMap<>();
                        errMap.put(constraintViolation.getPropertyPath().toString(),
                                   constraintViolation.getMessage());
                        return errMap;
                         }).toList();
            return responseEntity.body(errors);

        }

        return responseEntity.build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){
        List<Map<String, String>> errorList = exception.getFieldErrors().stream().map(fieldError -> {
            Map<String, String> error = new HashMap<>();
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
            return error;
        }).toList();
        return ResponseEntity.badRequest().body(errorList);
    };
}
