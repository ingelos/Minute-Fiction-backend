package com.mf.minutefictionbackend.dtos.outputDtos;

import java.time.LocalDate;
import java.util.List;

public class StoryOutputDto {

    public Long id;
    public String title;
    public String content;
    public String status;
    public LocalDate publishDate;

    public AuthorProfileOutputDto authorProfileOutputDto;
    public ThemeOutputDto themeOutputDto;
    public List<CommentOutputDto> commentOutputDtoList;



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

    public AuthorProfileOutputDto getAuthorProfileOutputDto() {
        return authorProfileOutputDto;
    }

    public void setAuthorProfileOutputDto(AuthorProfileOutputDto authorProfileOutputDto) {
        this.authorProfileOutputDto = authorProfileOutputDto;
    }

    public ThemeOutputDto getThemeOutputDto() {
        return themeOutputDto;
    }

    public void setThemeOutputDto(ThemeOutputDto themeOutputDto) {
        this.themeOutputDto = themeOutputDto;
    }

    public List<CommentOutputDto> getCommentOutputDtoList() {
        return commentOutputDtoList;
    }

    public void setCommentOutputDtoList(List<CommentOutputDto> commentOutputDtoList) {
        this.commentOutputDtoList = commentOutputDtoList;
    }
}
