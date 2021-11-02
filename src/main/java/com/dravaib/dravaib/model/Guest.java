package com.dravaib.dravaib.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.dravaib.dravaib.model.embed.PersonDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Person details should be populated")
    @Embedded
    @Valid
    private PersonDetails personDetails;

    @JsonIgnore
    @ManyToMany(mappedBy = "guests")
    Collection<Visit> visits;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PersonDetails getPersonDetails() {
        return this.personDetails;
    }

    public void setPersonDetails(PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

    public Collection<Visit> getVisits() {
        return this.visits;
    }

    public void setVisits(Collection<Visit> visits) {
        this.visits = visits;
    }

}
