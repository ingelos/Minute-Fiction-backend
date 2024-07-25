package com.mf.minutefictionbackend.dtos.outputDtos;

import com.mf.minutefictionbackend.models.AuthorProfile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorProfileOutputDto {

    public String username;
    public String firstname;
    public String lastname;
    public String bio;
    public LocalDate dob;

    public List<String> storyTitles;
//    public UserOutputDto user;



    public AuthorProfileOutputDto() {

    }

    public AuthorProfileOutputDto(String username, String firstname, String lastname, String bio, LocalDate dob) {
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

    public List<String> getStoryTitles() {
        return storyTitles;
    }

    public void setStoryTitles(List<String> storyTitles) {
        this.storyTitles = storyTitles;
    }

//    public List<StoryOutputDto> getStories() {
//        return stories;
//    }
//
//    public void setStories(List<StoryOutputDto> stories) {
//        this.stories = stories;
//    }
//
//    public UserOutputDto getUser() {
//        return user;
//    }
//
//    public void setUser(UserOutputDto user) {
//        this.user = user;
//    }
}
