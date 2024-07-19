package com.mf.minutefictionbackend.dtos.outputDtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CommentOutputDto {

    public Long id;
    public String content;
    public LocalDateTime created;


    public StoryOutputDto story;
    public UserOutputDto user;


    public CommentOutputDto() {

    }

    public CommentOutputDto(Long id, String content, LocalDateTime created) {
        this.id = id;
        this.content = content;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public StoryOutputDto getStory() {
        return story;
    }

    public void setStory(StoryOutputDto story) {
        this.story = story;
    }

    public UserOutputDto getUser() {
        return user;
    }

    public void setUser(UserOutputDto user) {
        this.user = user;
    }
}
