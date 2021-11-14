package com.dravaib.dravaib.controller;

import java.util.Collection;

import com.dravaib.dravaib.model.Country;
import com.dravaib.dravaib.repository.CountryRepository;
import com.dravaib.dravaib.utils.ListUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/country")
public class CountryController {
    @Autowired
    private CountryRepository countryRepository;

    @GetMapping
    @Operation(summary = "Lists all countries", tags = { "country" })
    public ResponseEntity<Collection<Country>> getCountries() {
        return ResponseEntity.ok(ListUtil.iterableToList(countryRepository.findAll()));
    }
}
