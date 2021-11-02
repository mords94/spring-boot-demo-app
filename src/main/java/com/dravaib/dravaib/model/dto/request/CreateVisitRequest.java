package com.dravaib.dravaib.model.dto.request;

import java.io.Serializable;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.dravaib.dravaib.model.Guest;
import com.dravaib.dravaib.model.Place;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateVisitRequest implements Serializable {

    private static final long serialVersionUID = 59482533250707L;

    @Valid
    @NotEmpty(message = "Guest list should not be empty")
    @JsonProperty("guests")
    Set<Guest> guests;

    @Valid
    @NotNull(message = "It is mandatory to select a place")
    Place place;

    public Place getPlace() {
        return this.place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public CreateVisitRequest(Set<Guest> guests, Place place) {
        this.guests = guests;
        this.place = place;
    }

    public CreateVisitRequest() {
    }

    public Set<Guest> getGuests() {
        return this.guests;
    }

    public void setGuests(Set<Guest> guests) {
        this.guests = guests;
    }

}
