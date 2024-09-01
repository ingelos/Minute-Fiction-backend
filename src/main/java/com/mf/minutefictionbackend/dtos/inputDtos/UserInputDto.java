package com.mf.minutefictionbackend.dtos.inputDtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserInputDto {

    @NotBlank(message = "Username is required")
    public String username;
    @NotBlank(message = "Password is required")
    public String password;
    @Email(message = "Invalid email")
    public String email;
    @NotNull
    public Boolean isSubscribedToMailing;

}
