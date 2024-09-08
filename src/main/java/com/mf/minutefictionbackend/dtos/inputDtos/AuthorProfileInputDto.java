package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorProfileInputDto {

    @NotBlank(message = "First name is required.")
    @Size(min=3, max=64)
    private String firstname;
    @NotBlank(message = "Last name is required.")
    @Size(min=3, max=64)
    private String lastname;
    @NotNull
    private String bio;
    private LocalDate dob;

}
