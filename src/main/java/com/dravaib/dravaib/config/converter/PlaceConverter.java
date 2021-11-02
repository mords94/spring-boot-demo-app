package com.dravaib.dravaib.config.converter;

import java.util.Optional;

import com.dravaib.dravaib.model.Place;
import com.dravaib.dravaib.repository.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PlaceConverter implements Converter<String, Optional<Place>> {

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public Optional<Place> convert(String id) {
        return placeRepository.findById(id);
    }
}
