package com.mf.minutefictionbackend.dtos.inputDtos;


import jakarta.validation.constraints.NotNull;

public class UserInputDto {


    @NotNull(message = "Username is required")
    public String username;
    @NotNull(message = "Password is required")
    public String password;




}
