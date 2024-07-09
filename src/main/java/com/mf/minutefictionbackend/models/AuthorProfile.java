package com.mf.minutefictionbackend.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profiles")
public class AuthorProfile {

    @Id
    private String username;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String bio;
    @Column
    private LocalDate dob;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "authorProfile")
    private List<Story> stories = new ArrayList<>();





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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }




    public User getUser() {
        return user;
    }

    public List<Story> getStories() {
        return stories;
    }


}
