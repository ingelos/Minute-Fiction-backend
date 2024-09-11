package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorityInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorityOutputDto;
import com.mf.minutefictionbackend.models.Authority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthorityMapper {


    public static Authority fromDto(AuthorityInputDto dto) {
        return new Authority(dto.getAuthority());
    }


    public static List<String> toAuthorityNames(Set<Authority> authorities) {
        List<String> authorityNames = new ArrayList<>();
        authorities.forEach(authority -> authorityNames.add(authority.getAuthority()));
        return authorityNames;
    }

    public static Set<Authority> fromDtos(Set<AuthorityInputDto> authorityDtos) {
        Set<Authority> authorities = new HashSet<>();
        if(authorityDtos != null) {
            authorityDtos.forEach(dto -> authorities.add(fromDto(dto)));
        }
        return authorities;
    }

    public static AuthorityOutputDto toDto(Authority authority) {
        AuthorityOutputDto dto = new AuthorityOutputDto();
        dto.setAuthority(authority.getAuthority());
        return dto;
    }

    public static Set<AuthorityOutputDto> toDtos (Set<Authority> authorities) {
        Set<AuthorityOutputDto> dtos = new HashSet<>();
        authorities.forEach(authority -> dtos.add(toDto(authority)));
        return dtos;
    }



}