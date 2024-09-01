package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MailingInputDto {

    @NotNull(message = "Subject is required.")
    public String subject;
    @NotNull(message = "Body is required.")
    public String body;
    @FutureOrPresent
    public LocalDate date;


}
