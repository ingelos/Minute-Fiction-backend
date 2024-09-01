package com.mf.minutefictionbackend.dtos.outputDtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ThemeOutputDto {

    private Long id;
    private String name;
    private String description;
    private LocalDate openDate;
    private LocalDate closingDate;

}
