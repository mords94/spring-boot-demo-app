package com.dravaib.dravaib.model.dto.request;

import com.dravaib.dravaib.model.embed.PersonDetails;
import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UpdateProfileRequest {
    @Embedded
    @Valid
    @NotNull
    private PersonDetails personDetails;

    public UpdateProfileRequest() {
    }

    public UpdateProfileRequest(String password, PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

    public PersonDetails getPersonDetails() {
        return this.personDetails;
    }

    public void setPersonDetails(PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

}
