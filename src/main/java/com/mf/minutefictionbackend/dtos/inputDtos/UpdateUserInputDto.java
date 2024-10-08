package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UpdateUserInputDto {

    @Size(min=8, message = "Password needs to be at least 8 characters long")
    private String newPassword;
    @Size(min=8, message = "Password needs to be at least 8 characters long")
    private String confirmPassword;
    @Email(message = "Invalid email")
    private String email;
    @NotNull
    private boolean subscribedToMailing;

    private Set<AuthorityInputDto> authorities = new HashSet<>();


}
