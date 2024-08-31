package com.mf.minutefictionbackend.dtos.outputDtos;

import com.mf.minutefictionbackend.enums.StoryStatus;

import java.time.LocalDate;

public class StoryOutputDto {

    public Long id;
    public String title;
    public String content;
    public StoryStatus status;
    public LocalDate publishDate;


    public String username;
    public String authorFirstname;
    public String authorLastname;
    public String themeName;



    public StoryOutputDto() {
    }

    public StoryOutputDto(Long id, String title, String content, StoryStatus status, LocalDate publishDate, String username, String authorFirstname, String authorLastname, String themeName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.publishDate = publishDate;
        this.username = username;
        this.authorFirstname = authorFirstname;
        this.authorLastname = authorLastname;
        this.themeName = themeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public StoryStatus getStatus() {
        return status;
    }

    public void setStatus(StoryStatus status) {
        this.status = status;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthorFirstname() {
        return authorFirstname;
    }

    public void setAuthorFirstname(String authorFirstname) {
        this.authorFirstname = authorFirstname;
    }

    public String getAuthorLastname() {
        return authorLastname;
    }

    public void setAuthorLastname(String authorLastname) {
        this.authorLastname = authorLastname;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }


}
