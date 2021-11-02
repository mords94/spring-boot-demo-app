package com.dravaib.dravaib.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Component
public class ErrorUtil {
    public Map<String, Object> getFieldErrorResponse(Errors result) {

        Map<String, Object> fielderror = new HashMap<>();
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors) {
            fielderror.put(error.getField(), error.getDefaultMessage());
        }
        return fielderror;
    }

    public ResponseEntity<Object> fieldErrorResponse(String message, Object fieldError) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", null);
        map.put("status", HttpStatus.UNPROCESSABLE_ENTITY);
        map.put("message", message);
        map.put("fieldError", fieldError);
        return new ResponseEntity<Object>(map, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ResponseEntity<?> createSingleFieldError(String field, String error) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> fieldError = new HashMap<>();
        fieldError.put(field, error);
        map.put("data", null);
        map.put("status", HttpStatus.UNPROCESSABLE_ENTITY);
        map.put("message", "Validation error");
        map.put("fieldError", fieldError);
        return new ResponseEntity<Object>(map, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
