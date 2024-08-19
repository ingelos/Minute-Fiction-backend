package com.mf.minutefictionbackend.dtos.outputDtos;

public class ProfilePhotoOutputDto {

    private String fileName;




    public ProfilePhotoOutputDto() {
    }

    public ProfilePhotoOutputDto(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
