package com.mf.minutefictionbackend.dtos.outputDtos;


import lombok.Data;

@Data
public class UserOutputDto {

    private String username;
    private String email;
    private Boolean isSubscribedToMailing;
    private Boolean hasAuthorProfile;

}
