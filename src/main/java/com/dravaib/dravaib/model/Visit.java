package com.dravaib.dravaib.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToMany
    @JoinTable(name = "visit_guests", joinColumns = @JoinColumn(name = "visit_id"), inverseJoinColumns = @JoinColumn(name = "guests_id"))
    @NotEmpty(message = "It is mandatory to add atleast one guest ot a user")
    @Valid
    @JsonProperty("guests")
    Collection<Guest> guests;

    @CreationTimestamp
    private Timestamp visitDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date finishDate;

    @ManyToOne
    @NotNull(message = "It is mandatory to select a place")
    @Valid
    private Place place;

    public Place getPlace() {
        return this.place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Collection<Guest> getGuests() {
        return this.guests;
    }

    public void setGuests(Collection<Guest> guests) {
        this.guests = guests;
    }

    public Timestamp getVisitDate() {
        return this.visitDate;
    }

    public void setVisitDate(Timestamp visitDate) {
        this.visitDate = visitDate;
    }

    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

}
