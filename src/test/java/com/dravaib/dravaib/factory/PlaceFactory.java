package com.dravaib.dravaib.factory;

import java.util.UUID;

import com.dravaib.dravaib.model.Place;
import com.dravaib.dravaib.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaceFactory extends Factory<Place> {

    @Autowired
    private AddressFactory addressFactory;

    public Place make(User owner) {
        return new Place(faker.university().name(), addressFactory.make(), owner);
    }

    public Place make() {
        return new Place();
    }

}
