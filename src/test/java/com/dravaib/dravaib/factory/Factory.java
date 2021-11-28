package com.dravaib.dravaib.factory;

import com.github.javafaker.Faker;

public abstract class Factory<T> {

    public Faker faker;

    public Factory() {
        this.faker = new Faker();
    }

    public abstract T make();
}
