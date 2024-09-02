package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MailingInputDto {

    @NotNull(message = "Subject is required.")
    private String subject;
    @NotNull(message = "Body is required.")
    private String body;
    @FutureOrPresent
    private LocalDate date;


}
