package com.dravaib.dravaib.seed;

import com.dravaib.dravaib.model.Country;
import com.dravaib.dravaib.repository.CountryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CountrySeed implements Seed {
    @Autowired
    private CountryRepository countryRepository;

    private String[] COUNTRIES = { "Hungary", "Germany", "Austria", "Chech Republic", "Slovakia" };

    public void run() {
        if (countryRepository.count() == 0) {
            Arrays.stream(COUNTRIES).forEach(country -> countryRepository.save(new Country(country)));
        }
    }
}
