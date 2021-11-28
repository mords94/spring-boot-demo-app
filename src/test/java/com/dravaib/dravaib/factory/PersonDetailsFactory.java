package com.dravaib.dravaib.factory;

import com.dravaib.dravaib.model.embed.PersonDetails;

import org.springframework.stereotype.Component;

@Component
public class PersonDetailsFactory extends Factory<PersonDetails> {

    @Override
    public PersonDetails make() {
        return new PersonDetails(faker.name().firstName(), faker.name().lastName(), faker.name().username(),
                faker.phoneNumber().phoneNumber());
    }
}
