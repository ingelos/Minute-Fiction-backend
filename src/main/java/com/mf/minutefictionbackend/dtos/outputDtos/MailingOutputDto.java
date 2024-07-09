package com.mf.minutefictionbackend.dtos.outputDtos;

import java.time.LocalDate;
import java.util.Set;

public class MailingOutputDto {

    public Long id;
    public String title;
    public String content;
    public LocalDate date;

    public Set<UserOutputDto> userOutputDtoSet;


    public MailingOutputDto() {

    }
    public MailingOutputDto(Long id, String title, String content, LocalDate date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
