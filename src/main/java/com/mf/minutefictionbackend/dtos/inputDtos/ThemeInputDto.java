package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

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


    public ThemeInputDto(String name, String description, LocalDate openDate, LocalDate closingDate) {
        this.name = name;
        this.description = description;
        this.openDate = openDate;
        this.closingDate = closingDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }
}
