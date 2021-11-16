package com.dravaib.dravaib.config.converter;

import java.util.Optional;

import com.dravaib.dravaib.model.Visit;
import com.dravaib.dravaib.repository.VisitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class VisitConverter implements Converter<String, Visit> {

    @Autowired
    private VisitRepository visitRepository;

    @Override
    public Visit convert(String id) {
        return visitRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found with id: " + id));
    }
}