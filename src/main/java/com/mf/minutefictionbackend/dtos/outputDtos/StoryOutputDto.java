package com.mf.minutefictionbackend.dtos.outputDtos;

import java.time.LocalDate;
import java.util.List;

public class StoryOutputDto {

    public Long id;
    public String title;
    public String content;
    public String status;
    public LocalDate publishDate;


    public AuthorProfileOutputDto authorProfile;
    public ThemeOutputDto theme;
    public List<CommentOutputDto> comments;



    public StoryOutputDto() {

    }

    public StoryOutputDto(Long id, String title, String content, String status, LocalDate publishDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.publishDate = publishDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public AuthorProfileOutputDto getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(AuthorProfileOutputDto authorProfile) {
        this.authorProfile = authorProfile;
    }

    public ThemeOutputDto getTheme() {
        return theme;
    }

    public void setTheme(ThemeOutputDto theme) {
        this.theme = theme;
    }

    public List<CommentOutputDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentOutputDto> comments) {
        this.comments = comments;
    }
}
