package com.mf.minutefictionbackend.dtos.outputDtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MailingOutputDto {

    private Long id;
    private String subject;
    private String body;
    private LocalDate date;

}
