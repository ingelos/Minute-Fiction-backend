package com.mf.minutefictionbackend.dtos.outputDtos;

import java.time.LocalDate;
import java.util.List;

public class ThemeOutputDto {

    public Long id;

    public String name;
    public String description;
    public LocalDate openDate;
    public LocalDate closingDate;




    public ThemeOutputDto(Long id, String name, String description, LocalDate openDate, LocalDate closingDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.openDate = openDate;
        this.closingDate = closingDate;
    }

    public ThemeOutputDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
