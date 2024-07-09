package com.mf.minutefictionbackend.dtos.outputDtos;

import java.util.List;

public class UserOutputDto {

    public String username;
    public String password;
    public String email;

    public AuthorProfileOutputDto authorProfile;
    public MailingOutputDto mailing;
    public List<CommentOutputDto> commentOutputDtoList;


    public UserOutputDto() {

    }

    public UserOutputDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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

    public AuthorProfileOutputDto getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(AuthorProfileOutputDto authorProfile) {
        this.authorProfile = authorProfile;
    }

    public MailingOutputDto getMailing() {
        return mailing;
    }

    public void setMailing(MailingOutputDto mailing) {
        this.mailing = mailing;
    }

    public List<CommentOutputDto> getCommentOutputDtoList() {
        return commentOutputDtoList;
    }

    public void setCommentOutputDtoList(List<CommentOutputDto> commentOutputDtoList) {
        this.commentOutputDtoList = commentOutputDtoList;
    }
}
