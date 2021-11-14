package com.dravaib.dravaib.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dravaib.dravaib.model.dto.response.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Component
public class ErrorUtil {
    public Map<String, String> getFieldErrorResponse(Errors result) {

        Map<String, String> fielderror = new HashMap<>();
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors) {
            fielderror.put(error.getField(), error.getDefaultMessage());
        }
        return fielderror;
    }

    public ResponseEntity<ErrorResponse> fieldErrorResponse(String message, Map<String, String> fieldError) {
        var errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, message, fieldError);
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ResponseEntity<ErrorResponse> createSingleFieldError(String field, String error) {
        Map<String, String> fieldError = new HashMap<>();
        fieldError.put(field, error);

        var errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Validation error", fieldError);
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
