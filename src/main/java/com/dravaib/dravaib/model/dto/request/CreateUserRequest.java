package com.dravaib.dravaib.model.dto.request;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.dravaib.dravaib.model.embed.PersonDetails;

import org.hibernate.validator.constraints.Length;

public class CreateUserRequest {
    @NotBlank(message = "Password is required")
    @Length(min = 6, max = 24)
    private String password;

    @Embedded
    @Valid
    @NotNull
    private PersonDetails personDetails;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String password, PersonDetails personDetails) {
        this.password = password;
        this.personDetails = personDetails;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PersonDetails getPersonDetails() {
        return this.personDetails;
    }

    public void setPersonDetails(PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

}
