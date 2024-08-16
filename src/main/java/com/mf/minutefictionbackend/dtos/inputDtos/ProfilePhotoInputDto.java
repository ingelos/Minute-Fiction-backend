package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.NotNull;

public class ProfilePhotoInputDto {

    @NotNull
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
