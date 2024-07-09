package com.mf.minutefictionbackend.dtos.outputDtos;

import java.time.LocalDate;

public class ThemeOutputDto {

    public Long id;
    public String name;
    public LocalDate openDate;
    public LocalDate closeDate;

    public ThemeOutputDto(Long id, String name, LocalDate openDate, LocalDate closeDate) {
        this.id = id;
        this.name = name;
        this.openDate = openDate;
        this.closeDate = closeDate;
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

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }
}
