package com.mf.minutefictionbackend.dtos.outputDtos;


public class UserOutputDto {

    public String username;
    public String email;
    public Boolean isSubscribedToMailing;
    public Boolean hasAuthorProfile;


    public UserOutputDto() {

    }

    public UserOutputDto(String username, String email, Boolean isSubscribedToMailing, Boolean hasAuthorProfile) {
        this.username = username;
        this.email = email;
        this.isSubscribedToMailing = isSubscribedToMailing;
        this.hasAuthorProfile = hasAuthorProfile;
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

    public Boolean getIsSubscribedToMailing() {
        return isSubscribedToMailing;
    }

    public void setIsSubscribedToMailing(Boolean isSubscribedToMailing) {
        this.isSubscribedToMailing = isSubscribedToMailing;
    }

    public Boolean getHasAuthorProfile() {
        return hasAuthorProfile;
    }

    public void setHasAuthorProfile(Boolean hasAuthorProfile) {
        this.hasAuthorProfile = hasAuthorProfile;
    }
}
