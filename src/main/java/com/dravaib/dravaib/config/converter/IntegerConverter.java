package com.dravaib.dravaib.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String id) {
        return Integer.parseInt(id);
    }
}
