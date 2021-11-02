package com.dravaib.dravaib.config;

import java.util.HashMap;
import java.util.Map;

import com.dravaib.dravaib.utils.ErrorUtil;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;

@RestControllerAdvice
public class ExceptionResolver {

    @Autowired
    private ErrorUtil errorUtil;

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUnprosseasableMsgException(HttpMessageNotReadableException msgNotReadable) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", null);
        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("message", "Invalid payload");
        return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return errorUtil.fieldErrorResponse("Validation error", errorUtil.getFieldErrorResponse(ex.getBindingResult()));
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> handleUnprosseasableMsgException(BindException bindException, WebRequest request) {
        return errorUtil.fieldErrorResponse("Binding error",
                errorUtil.getFieldErrorResponse(bindException.getBindingResult()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found...");

    }
}