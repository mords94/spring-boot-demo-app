package com.dravaib.dravaib.model.dto.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = -802424924046844L;

    private int status;
    private String message;
    private Map<String, String> fieldMessage = new HashMap<String, String>();

    public ErrorResponse(HttpStatus status, String message, Map<String, String> fieldMessage) {
        this.status = status.value();
        this.message = message;
        this.fieldMessage = fieldMessage;
    }

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }

    public Map<String, String> getFieldMessage() {
        return this.fieldMessage;
    }

    public void setFieldMessage(Map<String, String> fieldMessage) {
        this.fieldMessage = fieldMessage;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
