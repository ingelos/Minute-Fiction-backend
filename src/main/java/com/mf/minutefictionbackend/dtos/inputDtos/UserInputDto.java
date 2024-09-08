package com.mf.minutefictionbackend.dtos.inputDtos;


import com.mf.minutefictionbackend.models.Authority;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserInputDto {

    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @Email(message = "Invalid email")
    private String email;
    @NotNull
    private boolean subscribedToMailing;
    private Set<AuthorityInputDto> authorities = new HashSet<>();


}
