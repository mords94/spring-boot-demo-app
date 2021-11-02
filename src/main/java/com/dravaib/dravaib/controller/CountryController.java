package com.dravaib.dravaib.controller;

import com.dravaib.dravaib.model.Country;
import com.dravaib.dravaib.repository.CountryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/country")
public class CountryController {
    @Autowired
    private CountryRepository countryRepository;

    @GetMapping
    public ResponseEntity<Iterable<Country>> getCountries() {
        return ResponseEntity.ok(countryRepository.findAll());
    }
}
