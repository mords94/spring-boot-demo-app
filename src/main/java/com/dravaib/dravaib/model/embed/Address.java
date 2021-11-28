package com.dravaib.dravaib.model.embed;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Address {
    // @ManyToOne(targetEntity = Country.class)
    // private Country country;

    @NotBlank
    private String city;

    @NotBlank
    private String addressLine;

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine() {
        return this.addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public Address(String city, String addressLine) {
        this.city = city;
        this.addressLine = addressLine;
    }

    public Address() {
    }

}
