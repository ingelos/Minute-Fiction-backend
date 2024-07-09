package com.mf.minutefictionbackend.dtos.outputDtos;

import java.time.LocalDate;

public class CommentOutputDto {

    public Long id;
    public String comment;
    public LocalDate date;

    public StoryOutputDto storyOutputDto;
    public UserOutputDto userOutputDto;


    public CommentOutputDto() {

    }

    public CommentOutputDto(Long id, String comment, LocalDate date) {
        this.id = id;
        this.comment = comment;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StoryOutputDto getStoryOutputDto() {
        return storyOutputDto;
    }

    public void setStoryOutputDto(StoryOutputDto storyOutputDto) {
        this.storyOutputDto = storyOutputDto;
    }

    public UserOutputDto getUserOutputDto() {
        return userOutputDto;
    }

    public void setUserOutputDto(UserOutputDto userOutputDto) {
        this.userOutputDto = userOutputDto;
    }
}
