package com.dravaib.dravaib.model.embed;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Embeddable
public class PersonDetails {
    @NotNull
    @NotBlank(message = "Firstname cannot be empty")
    private String firstName;

    @NotNull
    @NotBlank(message = "Lastname cannot be empty")
    private String lastName;

    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotNull
    @NotBlank(message = "Firstname cannot be null")
    private String phone;

    public PersonDetails(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public PersonDetails() {
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
