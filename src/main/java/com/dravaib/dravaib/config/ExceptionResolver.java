package com.dravaib.dravaib.config;

import com.dravaib.dravaib.model.dto.response.ErrorResponse;
import com.dravaib.dravaib.utils.ErrorUtil;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@RestControllerAdvice
public class ExceptionResolver {

    @Autowired
    private ErrorUtil errorUtil;

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
    public ResponseEntity<?> handleMessageNotReadableException(HttpMessageNotReadableException msgNotReadable) {
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid payload");
        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return errorUtil.fieldErrorResponse("Validation error", errorUtil.getFieldErrorResponse(ex.getBindingResult()));
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleUnprosseasableMsgException(BindException bindException,
            WebRequest request) {
        return errorUtil.fieldErrorResponse("Binding error",
                errorUtil.getFieldErrorResponse(bindException.getBindingResult()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {
        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, "Resource not found");
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> responseStatusException(ResponseStatusException e, WebRequest request) {
        var errorResponse = new ErrorResponse(e.getStatus(), e.getReason());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }
}