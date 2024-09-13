package com.mf.minutefictionbackend.dtos.outputDtos;

import com.mf.minutefictionbackend.models.Authority;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class UserOutputDto {

    private String username;
    private String email;
    private boolean subscribedToMailing;
    private boolean hasAuthorProfile;
    private List<String> authorities;

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = new ArrayList<>();
        authorities.forEach(authority -> this.authorities.add(authority.getAuthority()));
    }

}
