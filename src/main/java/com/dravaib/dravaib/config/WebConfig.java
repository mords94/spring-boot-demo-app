package com.dravaib.dravaib.config;

import com.dravaib.dravaib.config.converter.PlaceConverter;
import com.dravaib.dravaib.config.converter.UUIDConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private PlaceConverter placeConverter;

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(WebConfig.class);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(placeConverter);
        registry.addConverter(new UUIDConverter());
    }
}
