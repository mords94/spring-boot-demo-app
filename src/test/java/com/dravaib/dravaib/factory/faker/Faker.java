package com.dravaib.dravaib.factory.faker;

import java.util.Locale;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

public class Faker {

    private FakeValuesService fakeValuesService;

    private static Faker instance = null;

    private Faker() {
        this.fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
    }

    public static FakeValuesService getService() {
        if (instance == null) {
            instance = new Faker();
        }

        return instance.fakeValuesService;
    }

}
