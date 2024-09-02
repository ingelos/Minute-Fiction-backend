package com.mf.minutefictionbackend.dtos.outputDtos;


import com.mf.minutefictionbackend.models.Authority;
import lombok.Data;

import java.util.Set;

@Data
public class UserOutputDto {

    private String username;
    private String email;
    private Boolean isSubscribedToMailing;
    private Boolean hasAuthorProfile;
    private Set<Authority> authorities;

}
