package com.mf.minutefictionbackend.dtos.outputDtos;


import com.mf.minutefictionbackend.dtos.dtos.ProfilePhotoDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorProfileOutputDto {

    private String username;
    private String firstname;
    private String lastname;
    private String bio;
    private LocalDate dob;
    private ProfilePhotoDto profilePhoto;

}
