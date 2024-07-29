package com.mf.minutefictionbackend.dtos.outputDtos;

import com.mf.minutefictionbackend.enums.StoryStatus;

import java.time.LocalDate;
import java.util.List;

public class StoryOutputDto {

    public Long id;
    public String title;
    public String content;
    public StoryStatus status;
    public LocalDate publishDate;

    public String authorUsername;
    public String themeName;
    public List<CommentOutputDto> comments;



    public StoryOutputDto() {
    }

    public StoryOutputDto(Long id, String title, String content, StoryStatus status, LocalDate publishDate) {
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

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public List<CommentOutputDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentOutputDto> comments) {
        this.comments = comments;
    }
}
