package com.dravaib.dravaib.config;

import com.dravaib.dravaib.config.converter.PlaceConverter;
import com.dravaib.dravaib.config.converter.UUIDConverter;
import com.dravaib.dravaib.logging.LoggerInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private PlaceConverter placeConverter;

    @Autowired
    private LoggerInterceptor loggerInterceptor;

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(WebConfig.class);
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(new Info().title("CovidTracker API").description("CovidTracker application for PPCU")
                .version("v1.0.0").license(new License().name("Apache 2.0")));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(placeConverter);
        registry.addConverter(new UUIDConverter());
    }

    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    // registry.addInterceptor(loggerInterceptor).addPathPatterns("/**");
    // }

}
