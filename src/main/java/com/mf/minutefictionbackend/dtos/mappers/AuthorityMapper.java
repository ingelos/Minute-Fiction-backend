package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorityInputDto;
import com.mf.minutefictionbackend.models.Authority;
import java.util.HashSet;

import java.util.Set;
public class AuthorityMapper {

    public static Set<Authority> fromDtoToSet(Set<AuthorityInputDto> authorityDtos) {
        Set<Authority> authorities = new HashSet<>();
        if(authorityDtos != null) {
            authorityDtos.forEach(dto -> authorities.add(fromDto(dto)));
        }
        return authorities;
    }

    public static Authority fromDto(AuthorityInputDto dto) {
        return new Authority(dto.getAuthority());
    }


}
