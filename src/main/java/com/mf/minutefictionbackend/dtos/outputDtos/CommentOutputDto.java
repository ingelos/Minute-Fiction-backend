package com.mf.minutefictionbackend.dtos.outputDtos;

import java.time.LocalDateTime;

public class CommentOutputDto {

    public Long id;
    public String content;
    public LocalDateTime created;

    public String username;
    public Long storyId;




    public CommentOutputDto() {

    }

    public CommentOutputDto(Long id, String content, LocalDateTime created, String username, Long storyId) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.username = username;
        this.storyId = storyId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }
}
