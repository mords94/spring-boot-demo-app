package com.dravaib.dravaib.config.converter;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UUIDConverter implements Converter<String, UUID> {
    public UUID convert(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
