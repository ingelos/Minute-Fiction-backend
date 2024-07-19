package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AuthorProfileInputDto {

    @NotBlank
    public String username;
    @NotBlank(message = "First name is required.")
    @Size(min=3, max=64)
    public String firstname;
    @NotBlank(message = "Last name is required.")
    @Size(min=3, max=64)
    public String lastname;
    @NotNull
    public String bio;
    public LocalDate dob;



    public AuthorProfileInputDto(String username, String firstname, String lastname, String bio, LocalDate dob) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.bio = bio;
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
