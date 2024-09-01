package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ThemeInputDto {

    @NotBlank(message = "Theme requires a name")
    public String name;
    @NotBlank(message = "Theme requires a description")
    public String description;
    @FutureOrPresent
    public LocalDate openDate;
    @NotNull(message = "Theme requires a closingDate")
    @Future
    public LocalDate closingDate;

}
