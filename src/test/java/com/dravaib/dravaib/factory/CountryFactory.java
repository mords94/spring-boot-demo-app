package com.dravaib.dravaib.factory;

import com.dravaib.dravaib.model.Country;

import org.springframework.stereotype.Component;

@Component
public class CountryFactory extends Factory<Country> {

    public Country make() {
        return new Country(faker.random().nextInt(1, 1000), faker.address().country());
    }
}
