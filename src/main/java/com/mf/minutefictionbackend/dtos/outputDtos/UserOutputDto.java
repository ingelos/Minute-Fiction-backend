package com.mf.minutefictionbackend.dtos.outputDtos;


public class UserOutputDto {

    public String username;
    public String email;
    public Boolean subscribedToMailing;

    public AuthorProfileOutputDto authorProfile;


    public UserOutputDto() {

    }

    public UserOutputDto(String username, String email, Boolean subscribedToMailing) {
        this.username = username;
        this.email = email;
        this.subscribedToMailing = subscribedToMailing;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSubscribedToMailing() {
        return subscribedToMailing;
    }

    public void setSubscribedToMailing(Boolean subscribedToMailing) {
        this.subscribedToMailing = subscribedToMailing;
    }

    public AuthorProfileOutputDto getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(AuthorProfileOutputDto authorProfile) {
        this.authorProfile = authorProfile;
    }


}
