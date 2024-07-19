package com.mf.minutefictionbackend.dtos.outputDtos;

import java.time.LocalDate;


public class MailingOutputDto {

    public Long id;
    public String subject;
    public String body;
    public LocalDate date;




    public MailingOutputDto() {
    }

    public MailingOutputDto(Long id, String subject, String body, LocalDate date) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
