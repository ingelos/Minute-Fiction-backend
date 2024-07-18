package com.mf.minutefictionbackend.dtos.outputDtos;

import java.util.List;

public class UserOutputDto {

    public String username;
    public String password;
    public String email;
    public Boolean subscribedToMailing;

    public AuthorProfileOutputDto authorProfile;

    public List<CommentOutputDto> commentOutputDtoList;


    public UserOutputDto() {

    }

    public UserOutputDto(String username, String password, String email, Boolean subscribedToMailing) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.subscribedToMailing = subscribedToMailing;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<CommentOutputDto> getCommentOutputDtoList() {
        return commentOutputDtoList;
    }

    public void setCommentOutputDtoList(List<CommentOutputDto> commentOutputDtoList) {
        this.commentOutputDtoList = commentOutputDtoList;
    }
}
