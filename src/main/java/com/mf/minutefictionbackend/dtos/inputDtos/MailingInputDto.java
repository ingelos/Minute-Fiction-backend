package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MailingInputDto {

    @NotNull(message = "Subject is required.")
    public String subject;
    @NotNull(message = "Body is required.")
    public String body;
    @FutureOrPresent
    public LocalDate date;


    public MailingInputDto(String subject, String body, LocalDate date) {
        this.subject = subject;
        this.body = body;
        this.date = date;
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
