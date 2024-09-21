package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfilePhotoInputDto {

    @NotNull
    private String fileName;

}
