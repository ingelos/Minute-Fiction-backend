package com.mf.minutefictionbackend.dtos.inputDtos;

import java.time.LocalDate;

public class MailingInputDto {

    public String title;
    public String content;
    public LocalDate date;

    public MailingInputDto(String title, String content, LocalDate date) {
        this.title = title;
        this.content = content;
        this.date = date;
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
