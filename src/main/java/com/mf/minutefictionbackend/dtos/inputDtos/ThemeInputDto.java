package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ThemeInputDto {

    @NotNull(message = "Theme requires a name")
    public String name;
    @FutureOrPresent
    public LocalDate openDate;
    @NotNull(message = "Theme requires a closingDate")
    @Future
    public LocalDate closingDate;


    public ThemeInputDto(String name, LocalDate openDate, LocalDate closingDate) {
        this.name = name;
        this.openDate = openDate;
        this.closingDate = closingDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
