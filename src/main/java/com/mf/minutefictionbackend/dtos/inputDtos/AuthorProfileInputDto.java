package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
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
    private String bio;
    @Past
    private LocalDate dob;

}
