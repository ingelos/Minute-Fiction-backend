package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class MailingInputDto {

    @NotNull(message = "Subject is required.")
    private String subject;
    @NotNull(message = "Body is required.")
    private String body;

}
