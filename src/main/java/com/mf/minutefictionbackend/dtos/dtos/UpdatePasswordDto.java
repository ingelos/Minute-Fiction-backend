package com.mf.minutefictionbackend.dtos.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordDto {

    @NotNull
    @Size(min = 8, message = "New password must be at least 8 characters long")
    private String newPassword;

    @NotNull
    @Size(min = 8, message = "New password must be at least 8 characters long")
    private String confirmPassword;



}
