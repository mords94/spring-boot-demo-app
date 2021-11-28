package com.dravaib.dravaib.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.dravaib.dravaib.model.embed.PersonDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "password")
  @NotBlank(message = "Password is required")
  @JsonIgnore(true)
  private String password;

  @Embedded
  @Valid
  @NotNull
  private PersonDetails personDetails;

  @ManyToOne(targetEntity = Role.class)
  private Role role;

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

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

  public User(Integer id, String password, PersonDetails personDetails, Role role) {
    this.id = id;
    this.password = password;
    this.personDetails = personDetails;
    this.role = role;
  }

  public User() {
  }

  public boolean hasRole(RoleType type) {
    return this.getRole().getRoleType().equals(type);
  }

  public boolean hasRole(RoleType[] types) {
    for (RoleType type : types) {
      if (type.equals(getRole().getRoleType())) {
        return true;
      }
    }

    return false;
  }
}