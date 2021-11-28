package com.dravaib.dravaib.factory;

import com.dravaib.dravaib.model.embed.Address;

import org.springframework.stereotype.Component;

@Component
public class AddressFactory extends Factory<Address> {
    public Address make() {
        return new Address(faker.address().cityName(), faker.address().streetAddress());
    }

}
